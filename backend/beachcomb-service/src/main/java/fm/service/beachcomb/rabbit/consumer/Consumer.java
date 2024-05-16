package fm.service.beachcomb.rabbit.consumer;

import fm.api.beachcomb.IVehicleDatafeedHandler;
import fm.api.datafeeder.VehicleDataDTO;
import fm.service.beachcomb.mongo.controller.MongoController;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Consumer implements IVehicleDatafeedHandler {

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
    @RabbitListener(queues = "#{queue.name}")
    @Override
    public void handleVehicleData(VehicleDataDTO vehicleData) {
        controller.saveVehicle(vehicleData);
    }
}
