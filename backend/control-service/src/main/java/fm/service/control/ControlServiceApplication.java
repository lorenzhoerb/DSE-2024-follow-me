package fm.service.control;

import fm.api.datafeeder.VehicleDataDTO;
import fm.api.datafeeder.VehicleStatusDTO;
import fm.api.inventory.dto.VehicleBaseDTO;
import fm.service.control.service.PairedProcessing;
import fm.service.control.service.UnpairedProcessing;
import fm.service.control.mongo.controller.MongoController;
import fm.service.control.rabbit.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
public class ControlServiceApplication implements CommandLineRunner {

    @Autowired
    MongoController controller;
    @Autowired
    PairedProcessing pairedProcessing;
    @Autowired
    UnpairedProcessing unpairedProcessing;

    public static void main(String[] args) {
        SpringApplication.run(ControlServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        controller.saveStatus(new VehicleStatusDTO("10",true,null));
        System.out.println(controller.getPaired());
        /**
         * This is main functionality
         **/
        List<VehicleBaseDTO> base;
        do {
            base = controller.getBaseList();
        } while (base.size() < 2);
        while (true) {
            pairedProcessing.processing();
            unpairedProcessing.processing();
        }
   }
}
