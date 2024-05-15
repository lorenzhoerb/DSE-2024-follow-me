package fm.service.beachcomb.rabbit.consumer;

import fm.api.datafeeder.VehicleDataDTO;
import fm.service.beachcomb.mongo.controller.MongoController;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    @Autowired
    MongoController controller;

    @RabbitListener(queues = "#{queue.name}")
    public void consume(VehicleDataDTO vehicle) {
        controller.saveVehicle(vehicle);
    }
}
