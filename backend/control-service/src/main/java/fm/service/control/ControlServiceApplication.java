package fm.service.control;

import fm.api.datafeeder.VehicleStatusDTO;
import fm.api.inventory.VehicleType;
import fm.api.inventory.dto.VehicleBaseDTO;
import fm.service.control.mongo.controller.MongoController;
import fm.service.control.rabbit.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ControlServiceApplication implements CommandLineRunner {

    @Autowired
    Producer producer;
    @Autowired
    MongoController controller;

    public static void main(String[] args) {
        SpringApplication.run(ControlServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<VehicleBaseDTO> vehicles = new ArrayList<>();
        vehicles.add(new VehicleBaseDTO("1", VehicleType.LV));
        vehicles.add(new VehicleBaseDTO("2", VehicleType.LV));
        vehicles.add(new VehicleBaseDTO("3", VehicleType.LV));
        vehicles.add(new VehicleBaseDTO("4", VehicleType.LV));
        for (VehicleBaseDTO vehicle : vehicles) {
            producer.sendMessage(vehicle);
        }
        List<VehicleStatusDTO> statusList = controller.getStatusList();
        for (VehicleStatusDTO status : statusList) {
            System.out.println(status);
        }
    }
}
