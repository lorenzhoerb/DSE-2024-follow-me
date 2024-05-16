package fm.service.beachcomb;

import fm.api.datafeeder.Location;
import fm.api.datafeeder.TargetControlDTO;
import fm.api.datafeeder.VehicleDataDTO;
import fm.service.beachcomb.mongo.controller.MongoController;
import fm.service.beachcomb.rabbit.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class BeachcombServiceApplication implements CommandLineRunner {

    @Autowired
    private Producer producer;
    @Autowired
    private MongoController controller;

    public static void main(String[] args) {
        SpringApplication.run(BeachcombServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
		VehicleDataDTO vehicle = new VehicleDataDTO("sdfsdfsdfsdf",new Location(),12.0,1,
				new TargetControlDTO(11.0,2), LocalDateTime.now());
		producer.sendMessage(vehicle);
		List<VehicleDataDTO> vehicles = controller.getVehicles();
		System.out.println(vehicles.get(1));
    }
}
