package fm.service.beachcomb.rabbit.consumer;

import fm.api.beachcomb.IVehicleDatafeedHandler;
import fm.api.datafeeder.VehicleDataDTO;
import fm.api.inventory.IVehicleEventHandler;
import fm.api.inventory.dto.VehicleBaseDTO;
import fm.service.beachcomb.mongo.controller.MongoController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Consumer implements IVehicleDatafeedHandler, IVehicleEventHandler {

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @Autowired
    MongoController controller;

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
        logger.info("handleVehicleData({})", vehicleData);
        controller.saveVehicle(vehicleData);
    }

    /**
     * Handles the creation of a vehicle. Upon the creation of a vehicle, this method will publish an event
     * under the topic "entity.vehicle.created".
     *
     * @param vehicleData Information about the newly created vehicle,
     *                    including its VIN (Vehicle Identification Number) and type (FV or LV).
     */
    @RabbitListener(queues = "#{beachcombVehicleEventQueue.name}")
    @Override
    public void handleVehicleCreated(VehicleBaseDTO vehicleData) {
        logger.info("handleVehicleCreated({})", vehicleData);
        controller.saveBase(vehicleData);
    }
}
