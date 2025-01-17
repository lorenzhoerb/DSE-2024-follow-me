package fm.service.control.service;

import fm.api.datafeeder.TargetControlDTO;
import fm.api.datafeeder.VehicleDataDTO;
import fm.api.datafeeder.VehicleStatusDTO;
import fm.api.inventory.VehicleType;
import fm.service.control.mongo.controller.MongoController;
import fm.service.control.rabbit.producer.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PairedProcessing {

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
        List<VehicleStatusDTO> vehicles = controller.getPaired();
        if (vehicles.isEmpty()) return;
        List<String> vinsToProcess = detectVins(vehicles);
        for (int i = 0; i < vinsToProcess.size(); i += 2) {
            checkTarget(vinsToProcess.get(i), vinsToProcess.get(i + 1));
        }
    }

    private List<String> detectVins(List<VehicleStatusDTO> vehicles) {
        List<String> vins = new ArrayList<>();
        for (VehicleStatusDTO v : vehicles) {
            if (!vins.contains(v.getVin()) && !vins.contains(v.getPairedVin())) {
                if (controller.findBaseByVin(v.getVin()).getVehicleType() == VehicleType.LV) {
                    vins.add(v.getVin());
                    vins.add(v.getPairedVin());
                } else {
                    vins.add(v.getPairedVin());
                    vins.add(v.getVin());
                }
            }
        }
        return vins;
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

    private void checkTarget(String lv, String fv) {
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
                    return;
                }
                c--;
            }
            unpair(lv, fv);
        }
    }

    private void updateFVControl(String fv, TargetControlDTO targetControlDTO) {
        VehicleStatusDTO statusDTO = controller.findByVin(fv);
        statusDTO.setTargetControl(targetControlDTO);
        controller.saveStatus(statusDTO);
        producer.sendStatus(fv, statusDTO);
    }

    private void unpair(String lv, String fv) {
        VehicleStatusDTO lStatus = controller.findByVin(lv);
        lStatus.setFollowMeModeActive(false);
        lStatus.setPairedVin(null);
        lStatus.setTargetControl(null);
        controller.saveStatus(lStatus);
        producer.sendStatus(lv, lStatus);
        VehicleStatusDTO fStatus = controller.findByVin(fv);
        fStatus.setFollowMeModeActive(false);
        fStatus.setPairedVin(null);
        fStatus.setTargetControl(null);
        controller.saveStatus(fStatus);
        producer.sendStatus(fv, fStatus);
    }

    private URI uriBuilder(String vin) {
        return UriComponentsBuilder.fromHttpUrl(url + "/api/beachcomb/vehicles/{vin}")
                .buildAndExpand(vin)
                .toUri();
    }

    private VehicleDataDTO request(String vin) {
        try {
            log.info("Calling beachcomb requestPaired...");
            return restTemplate.getForObject(uriBuilder(vin), VehicleDataDTO.class);
        } catch (RestClientException e) {
            log.error("Error: ", e);
            throw new RuntimeException(e);
        }
    }
}
