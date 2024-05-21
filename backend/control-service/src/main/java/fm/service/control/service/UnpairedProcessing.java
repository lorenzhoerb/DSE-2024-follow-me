package fm.service.control.service;

import fm.api.datafeeder.TargetControlDTO;
import fm.api.datafeeder.VehicleDataDTO;
import fm.api.datafeeder.VehicleStatusDTO;
import fm.api.inventory.VehicleType;
import fm.service.control.mongo.controller.MongoController;
import fm.service.control.rabbit.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class UnpairedProcessing {

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
        List<VehicleStatusDTO> vehicles = controller.getNotPaired();
        if (vehicles.isEmpty()) return;
        List<String> fvs = getFVs(vehicles);
        if (fvs.isEmpty()) return;
        for (String v : fvs) {
            List<VehicleDataDTO> candidates = candidates(v, controller.findBaseByVin(v).getVehicleType());
            if (candidates.isEmpty()) return;
            VehicleDataDTO following = request(v);
            for (VehicleDataDTO vehicle : candidates) {
                if (controller.findByVin(vehicle.getVin()).getPairedVin() == null) {
                    if (tryMatching(vehicle, following)) return;
                    else changeStatusUnpair(vehicle.getVin(), following.getVin());
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
                    if (tryMatching(leading, vehicle)) return;
                    else changeStatusUnpair(leading.getVin(), vehicle.getVin());
                }
            }
        }
    }

    private boolean tryMatching(VehicleDataDTO leading, VehicleDataDTO following) {
        changeStatusPair(leading.getVin(), following.getVin(), new TargetControlDTO(leading.getVelocity(), leading.getLane()));
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
        URI uri = UriComponentsBuilder.fromHttpUrl(url + "beachcomb/{vin}")
                .queryParam("type", type)
                .buildAndExpand(vin)
                .toUri();
        return uri;
    }

    private URI uriBuilder(String vin) {
        return UriComponentsBuilder.fromHttpUrl(url + "beachcomb/vehicles/{vin}")
                .buildAndExpand(vin)
                .toUri();
    }

    private URI uriBuilder() {
        return UriComponentsBuilder.fromHttpUrl(url + "beachcomb/vehicles").build().toUri();
    }

    private VehicleDataDTO request(String vin) {
        return restTemplate.getForObject(uriBuilder(vin), VehicleDataDTO.class);
    }

    private List<VehicleDataDTO> candidates(String vin, VehicleType type) {
        return restTemplate.exchange(uriBuilder(vin, type), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<VehicleDataDTO>>() {
                }).getBody();
    }

    public List<VehicleDataDTO> allData() {
        return restTemplate.exchange(uriBuilder(), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<VehicleDataDTO>>() {
                }).getBody();
    }


    private List<String> getLVs(List<VehicleStatusDTO> vehicles) {
        List<String> lvs = new ArrayList<>();
        for (VehicleStatusDTO v : vehicles) {
            if (controller.findBaseByVin(v.getVin()).getVehicleType() == VehicleType.LV)
                lvs.add(v.getVin());
        }
        return lvs;
    }

    private List<String> getFVs(List<VehicleStatusDTO> vehicles) {
        List<String> fvs = new ArrayList<>();
        for (VehicleStatusDTO v : vehicles) {
            if (controller.findBaseByVin(v.getVin()).getVehicleType() == VehicleType.FV)
                fvs.add(v.getVin());
        }
        return fvs;
    }
}
