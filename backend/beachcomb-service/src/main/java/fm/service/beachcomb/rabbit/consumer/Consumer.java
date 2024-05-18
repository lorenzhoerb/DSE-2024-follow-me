package fm.service.beachcomb.rabbit.consumer;

import fm.api.beachcomb.IVehicleDatafeedHandler;
import fm.api.datafeeder.VehicleDataDTO;
import fm.api.inventory.IVehicleEventHandler;
import fm.api.inventory.dto.VehicleBaseDTO;
import fm.service.beachcomb.mongo.controller.MongoController;
import fm.service.beachcomb.rabbit.producer.Producer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Consumer implements IVehicleDatafeedHandler, IVehicleEventHandler {

    @Autowired
    MongoController controller;
    @Autowired
    Producer producer;

    /**
     * Handles real-time vehicle data received via RabbitMQ under the topic "data.beachcomb".
     *
     * @param vehicleData The data of the vehicle to be processed, typically containing various parameters and metrics.
     *                    For Leading Vehicles (LV), the target control data is provided.
     *                    For Following Vehicles (FV), the target control is null.
     *                    All other values are assumed to be provided.
     */
    @RabbitListener(queues = "${fromDatafeeder.queue.name}")
    @Override
    public void handleVehicleData(VehicleDataDTO vehicleData) {
        controller.saveVehicle(vehicleData);
    }

    /**
     * Handles the creation of a vehicle. Upon the creation of a vehicle, this method will publish an event
     * under the topic "entity.vehicle.created".
     *
     * @param vehicleData Information about the newly created vehicle,
     *                    including its VIN (Vehicle Identification Number) and type (FV or LV).
     */
    @RabbitListener(queues = "${fromInventory.queue.name}")
    @Override
    public void handleVehicleCreated(VehicleBaseDTO vehicleData) {
        controller.saveBase(vehicleData);
    }

    @RabbitListener(queues = "${requestInfo.queue.name}")
    public void getRequest(String vin) {
        VehicleDataDTO vehicle = controller.findVehicleByVin(vin);
        producer.sendRespond(vehicle);
    }
}
