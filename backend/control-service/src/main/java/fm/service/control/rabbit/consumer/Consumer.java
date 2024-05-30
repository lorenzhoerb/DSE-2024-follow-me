package fm.service.control.rabbit.consumer;

import fm.api.datafeeder.VehicleStatusDTO;
import fm.api.inventory.IVehicleEventHandler;
import fm.api.inventory.dto.VehicleBaseDTO;
import fm.service.control.mongo.controller.MongoController;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Consumer implements IVehicleEventHandler {

    @Autowired
    MongoController controller;

    /**
     * Handles the creation of a vehicle. Upon the creation of a vehicle, this method will publish an event
     * under the topic "entity.vehicle.created".
     *
     * @param vehicleData Information about the newly created vehicle,
     *                    including its VIN (Vehicle Identification Number) and type (FV or LV).
     */
    @RabbitListener(queues = "#{controlVehicleEventQueue.name}")
    @Override
    public void handleVehicleCreated(VehicleBaseDTO vehicleData) {
        VehicleStatusDTO status = new VehicleStatusDTO();
        status.setVin(vehicleData.getVin());
        status.setFollowMeModeActive(false);
        status.setPairedVin(null);
        status.setTargetControl(null);
        controller.saveStatus(status);
        controller.saveBase(vehicleData);
    }
}
