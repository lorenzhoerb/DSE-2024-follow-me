package fm.service.control;

import fm.api.datafeeder.VehicleDataDTO;
import fm.api.inventory.dto.VehicleBaseDTO;
import fm.service.control.service.PairedProcessing;
import fm.service.control.service.UnpairedProcessing;
import fm.service.control.mongo.controller.MongoController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ControlServiceApplication implements CommandLineRunner {

    @Autowired
    MongoController controller;
    @Autowired
    PairedProcessing pairedProcessing;
    @Autowired
    UnpairedProcessing unpairedProcessing;
    @Value("${vehicles.to.expect}")
    private int amountOfVehicles;

    public static void main(String[] args) {
        SpringApplication.run(ControlServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        /**
         * Some checks to begin
         **/
        controller.freshStart();
        List<VehicleBaseDTO> base;
        do {
            base = controller.getBaseList();
        } while (base.size() < amountOfVehicles);
        List<VehicleDataDTO> vehicles = new ArrayList<>();
        do {
            try {
                vehicles = unpairedProcessing.allData();
            } catch (Exception e) {
            }
        } while (vehicles.size() < base.size());
        /**
         * This is main functionality
         **/
        while (true) {
            pairedProcessing.processing();
            unpairedProcessing.processing();
        }
    }
}
