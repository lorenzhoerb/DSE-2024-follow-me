package fm.service.beachcomb;

import fm.api.datafeeder.Location;
import fm.api.datafeeder.TargetControlDTO;
import fm.api.datafeeder.VehicleDataDTO;
import fm.api.inventory.VehicleType;
import fm.api.inventory.dto.VehicleBaseDTO;
import fm.service.beachcomb.mongo.controller.MongoController;
import fm.service.beachcomb.rabbit.producer.Producer;
import fm.service.beachcomb.rest.FindNearest;
import fm.service.beachcomb.rest.MatchingRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BeachcombServiceApplication implements CommandLineRunner {

//    @Autowired
//    private Producer producer;
//    @Autowired
//    private MongoController controller;
//    @Autowired
//    FindNearest nearest;

    public static void main(String[] args) {
        SpringApplication.run(BeachcombServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        VehicleDataDTO vehicle = new VehicleDataDTO("3", new Location(), 12.0, 1,
//                new TargetControlDTO(11.0, 2), LocalDateTime.now());
//        producer.sendMessage(vehicle);
//        Thread.sleep(1000);
//        List<VehicleDataDTO> vehicles = controller.getVehicles();
//        for (VehicleDataDTO v : vehicles) {
//            System.out.println(v);
//        }
//        List<VehicleBaseDTO> base = new ArrayList<>();
//        base.add(new VehicleBaseDTO("123", VehicleType.LV));
//        base.add(new VehicleBaseDTO("321", VehicleType.LV));
//        producer.sendMessage(base.get(0));
//        producer.sendMessage(base.get(1));
//        Thread.sleep(1000);
//        base = controller.getBaseList();
//        for (VehicleBaseDTO v : base) {
//            System.out.println(v);
//        }
    }
}
