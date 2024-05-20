package fm.service.control.service;

import fm.api.datafeeder.TargetControlDTO;
import fm.api.datafeeder.VehicleDataDTO;
import fm.api.datafeeder.VehicleStatusDTO;
import fm.api.inventory.VehicleType;
import fm.service.control.mongo.controller.MongoController;
import fm.service.control.rabbit.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    /**
     * What TargetControl should LV have? (now it's always null)
     **/
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
            List<VehicleDataDTO> candidates =
                    restTemplate.getForObject(uriBuilder(v, controller.findBaseByVin(v).getVehicleType()), List.class);
            if (candidates.isEmpty()) return;
            producer.sendRequestByVin(v);
            VehicleDataDTO following = controller.findVehicleByVin(v);
            for (VehicleDataDTO vehicle : candidates) {
                if (controller.findByVin(vehicle.getVin()).getPairedVin() == null) {
                    tryMatching(vehicle, following);
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
            List<VehicleDataDTO> candidates =
                    restTemplate.getForObject(uriBuilder(v, controller.findBaseByVin(v).getVehicleType()), List.class);
            if (candidates.isEmpty()) return;
            producer.sendRequestByVin(v);
            VehicleDataDTO leading = controller.findVehicleByVin(v);
            for (VehicleDataDTO vehicle : candidates) {
                if (controller.findByVin(vehicle.getVin()).getPairedVin() == null) {
                    tryMatching(leading, vehicle);
                }
            }
        }
    }

    private void tryMatching(VehicleDataDTO leading, VehicleDataDTO following) {
        changeStatusPair(leading.getVin(), following.getVin(), leading.getTargetControl());
        if (!checkTarget(leading.getVin(), following.getVin()))
            changeStatusUnpair(leading.getVin(), following.getVin());
    }

    private boolean checkTarget(String lv, String fv) {
        producer.sendRequestByVin(lv);
        producer.sendRequestByVin(fv);
        VehicleDataDTO leading = controller.findVehicleByVin(lv);
        VehicleDataDTO following = controller.findVehicleByVin(fv);
        TargetControlDTO leadingTarget = leading.getTargetControl();
        if (leadingTarget.getTargetLane() != following.getLane()
                || leadingTarget.getTargetVelocity() != following.getVelocity()) {
            int c = 4;
            while (c > 0) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                producer.sendRequestByVin(lv);
                producer.sendRequestByVin(fv);
                leading = controller.findVehicleByVin(lv);
                following = controller.findVehicleByVin(fv);
                leadingTarget = leading.getTargetControl();
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
        fvStatus.setPairedVin(fv);
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
