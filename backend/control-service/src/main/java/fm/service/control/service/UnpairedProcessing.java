package fm.service.control.service;

import fm.api.common.EventMessageDTO;
import fm.api.datafeeder.TargetControlDTO;
import fm.api.datafeeder.VehicleDataDTO;
import fm.api.datafeeder.VehicleStatusDTO;
import fm.api.inventory.VehicleType;
import fm.service.control.mongo.controller.MongoController;
import fm.service.control.rabbit.producer.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UnpairedProcessing {

    @Autowired
    EventMessageService eventMessageService;
    @Autowired
    MongoController controller;
    @Autowired
    Producer producer;
    @Autowired
    RestTemplate restTemplate;
    @Value("${send.rest.requests.to}")
    private String url;
    @Value("${repeat.sleep}")
    private int repeat;
    @Value("${sleep.time}")
    private int sleep;

    public void processing() {
        processLVs();
        processFVs();
    }

    private void processFVs() {
        log.info("started processFVs()");
        List<VehicleStatusDTO> vehicles = controller.getNotPaired();
        log.info("not Paired vehicles: {}", vehicles);
        if (vehicles.isEmpty()) return;
        List<String> fvs = getFVs(vehicles);
        if (fvs.isEmpty()) return;
        for (String v : fvs) {
            List<VehicleDataDTO> candidates = candidates(v, controller.findBaseByVin(v).getVehicleType());
            if (candidates.isEmpty()) return;
            VehicleDataDTO following = request(v);
            for (VehicleDataDTO vehicle : candidates) {
                if (controller.findByVin(vehicle.getVin()).getPairedVin() == null) {
                    if (tryMatching(vehicle, following)) {
                        return;
                    } else {
                        changeStatusUnpair(vehicle.getVin(), following.getVin());
                    }
                }
            }
        }
    }

    private void processLVs() {
        List<VehicleStatusDTO> vehicles = controller.getNotPaired();
        if (vehicles.isEmpty()) return;
        List<String> lvs = getLVs(vehicles);
        if (lvs.isEmpty()) return;
        for (String v : lvs) {
            List<VehicleDataDTO> candidates = candidates(v, controller.findBaseByVin(v).getVehicleType());
            if (candidates.isEmpty()) return;
            VehicleDataDTO leading = request(v);
            for (VehicleDataDTO vehicle : candidates) {
                if (controller.findByVin(vehicle.getVin()).getPairedVin() == null) {
                    if (tryMatching(leading, vehicle)) {
                        return;
                    } else {
                        changeStatusUnpair(leading.getVin(), vehicle.getVin());
                    }
                }
            }
        }
    }

    private boolean tryMatching(VehicleDataDTO leading, VehicleDataDTO following) {
        changeStatusPair(leading.getVin(), following.getVin(),
                new TargetControlDTO(leading.getVelocity(), leading.getLane()));
        return checkTarget(leading.getVin(), following.getVin());
    }

    private boolean checkTarget(String lv, String fv) {
        VehicleDataDTO leading = request(lv);
        VehicleDataDTO following = request(fv);
        TargetControlDTO leadingTarget = getLeadingTarget(leading);
        if (leadingTarget.getTargetLane() != following.getLane()
                || leadingTarget.getTargetVelocity() != following.getVelocity()) {
            int c = repeat;
            while (c > 0) {
                updateFVControl(fv, leadingTarget);
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                }
                leading = request(lv);
                following = request(fv);
                leadingTarget = getLeadingTarget(leading);
                if (leadingTarget.getTargetLane() == following.getLane()
                        && leadingTarget.getTargetVelocity() == following.getVelocity()) {
                    return true;
                }
                c--;
            }
            return false;
        }
        return true;
    }

    private TargetControlDTO getLeadingTarget(VehicleDataDTO data) {
        int c = 5;
        TargetControlDTO target;
        do {
            c--;
            data = request(data.getVin());
            target = data.getTargetControl();
        } while (target == null);
        return target;
    }

    private void changeStatusUnpair(String lv, String fv) {
        VehicleStatusDTO lvStatus = controller.findByVin(lv);
        VehicleStatusDTO fvStatus = controller.findByVin(fv);
        lvStatus.setFollowMeModeActive(false);
        lvStatus.setPairedVin(null);
        controller.saveStatus(lvStatus);
        producer.sendStatus(lv, lvStatus);
        fvStatus.setFollowMeModeActive(false);
        fvStatus.setPairedVin(null);
        fvStatus.setTargetControl(null);
        controller.saveStatus(fvStatus);
        producer.sendStatus(fv, fvStatus);
    }

    private void changeStatusPair(String lv, String fv, TargetControlDTO target) {
        VehicleStatusDTO lvStatus = controller.findByVin(lv);
        VehicleStatusDTO fvStatus = controller.findByVin(fv);
        lvStatus.setFollowMeModeActive(true);
        lvStatus.setPairedVin(fv);
        controller.saveStatus(lvStatus);
        producer.sendStatus(lv, lvStatus);
        fvStatus.setFollowMeModeActive(true);
        fvStatus.setPairedVin(lv);
        fvStatus.setTargetControl(target);
        controller.saveStatus(fvStatus);
        producer.sendStatus(fv, fvStatus);
    }

    private URI uriBuilder(String vin, VehicleType type) {
        return UriComponentsBuilder.fromHttpUrl(url + "/api/beachcomb/{vin}")
                .queryParam("type", type)
                .buildAndExpand(vin)
                .toUri();
    }

    private URI uriBuilder(String vin) {
        return UriComponentsBuilder.fromHttpUrl(url + "/api/beachcomb/vehicles/{vin}")
                .buildAndExpand(vin)
                .toUri();
    }

    private URI uriBuilder() {
        return UriComponentsBuilder.fromHttpUrl(url + "/api/beachcomb/vehicles").build().toUri();
    }

    private VehicleDataDTO request(String vin) {
        try {
            log.info("Calling beachcomb requestUnpaired...");
            return restTemplate.getForObject(uriBuilder(vin), VehicleDataDTO.class);
        } catch (RestClientException e) {
            log.error("Error: ", e);
            throw new RuntimeException(e);
        }
    }

    private List<VehicleDataDTO> candidates(String vin, VehicleType type) {
        try {
            log.info("Calling beachcomb candidates...");
            var a = restTemplate.exchange(uriBuilder(vin, type), HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<VehicleDataDTO>>() {
                    }).getBody();
            log.info("candidates: {}", a.stream().map(s -> s.toString()).toList());
            return a;
        } catch (RestClientException e) {
            log.error("Error: ", e);
            throw new RuntimeException(e);
        }
    }

    public List<VehicleDataDTO> allData() {
        try {
            var a = restTemplate.exchange(uriBuilder(), HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<VehicleDataDTO>>() {
                    }).getBody();
            log.info("allData: {}", String.join(", ", a.stream().map(s -> s.toString()).toList()));
            return a;
        } catch (RestClientException e) {
            log.error("Error: ", e);
            throw new RuntimeException(e);
        }
    }


    private List<String> getLVs(List<VehicleStatusDTO> vehicles) {
        List<String> lvs = new ArrayList<>();
        for (VehicleStatusDTO v : vehicles) {
            if (controller.findBaseByVin(v.getVin()).getVehicleType() == VehicleType.LV) {
                lvs.add(v.getVin());
            }
        }
        return lvs;
    }

    private List<String> getFVs(List<VehicleStatusDTO> vehicles) {
        List<String> fvs = new ArrayList<>();
        for (VehicleStatusDTO v : vehicles) {
            if (controller.findBaseByVin(v.getVin()).getVehicleType() == VehicleType.FV) {
                fvs.add(v.getVin());
            }
        }
        return fvs;
    }

    private void updateFVControl(String fv, TargetControlDTO targetControlDTO) {
        VehicleStatusDTO statusDTO = controller.findByVin(fv);
        statusDTO.setTargetControl(targetControlDTO);
        controller.saveStatus(statusDTO);
        producer.sendStatus(fv, statusDTO);
    }

    private void sendUnpairEvent(VehicleDataDTO leading, VehicleDataDTO following, TargetControlDTO leadingTarget) {
        log.info("Sending unpaired event to event service.");
        String leadingVin = leading.getVin();
        String followVin = following.getVin();
        String message = String.format(
                "Control Service: Unpaired Vehicles - Following Vehicle (VIN: %s) and Leading Vehicle (VIN: %s). " +
                        "Reason: Target conditions (velocity: %.2f km/h, lane: %d) were not met. " +
                        "Current conditions (velocity: %.2f km/h, lane: %d) were not achieved within %d seconds.",
                followVin, leadingVin,
                leadingTarget.getTargetVelocity(), leadingTarget.getTargetLane(),
                following.getVelocity(), following.getLane(),
                (repeat * sleep) / 1000
        );
        eventMessageService.sendEvent(new EventMessageDTO(message));
    }
}
