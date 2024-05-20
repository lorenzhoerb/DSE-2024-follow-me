package fm.service.control.service;

import fm.api.datafeeder.TargetControlDTO;
import fm.api.datafeeder.VehicleDataDTO;
import fm.api.datafeeder.VehicleStatusDTO;
import fm.api.inventory.VehicleType;
import fm.service.control.mongo.controller.MongoController;
import fm.service.control.rabbit.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PairedProcessing {

    @Autowired
    MongoController controller;
    @Autowired
    Producer producer;

    /**
     * What is TargetControl for lv and fv after unpairing? (now is null)
     **/

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

    private void checkTarget(String lv, String fv) {
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
                    return;
                }
                c--;
            }
            unpair(lv, fv);
        }
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
}
