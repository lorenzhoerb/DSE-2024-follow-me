package fm.service.control;

import fm.api.datafeeder.VehicleDataDTO;
import fm.api.inventory.dto.VehicleBaseDTO;
import fm.service.control.mongo.controller.MongoController;
import fm.service.control.service.PairedProcessing;
import fm.service.control.service.UnpairedProcessing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ControlServiceApplication implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(ControlServiceApplication.class);

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
        logger.info("started control loop");
        controller.freshStart();
        List<VehicleBaseDTO> base;
        do {
            base = controller.getBaseList();
            logger.info("checking vehicles: expected: {}, current: {}", amountOfVehicles, base.size());
            logger.info("vehicles from Beachcomb: {}", String.join(", ", base.stream().map(VehicleBaseDTO::toString).toList()));
        } while (base.size() < amountOfVehicles);
        logger.info("starting main loop");
        List<VehicleDataDTO> vehicles = new ArrayList<>();
        do {
            try {
                vehicles = unpairedProcessing.allData();
                if(vehicles == null ) {
                    logger.info("vehicles are null");
                } else if (vehicles.size() == 0) {
                    logger.info("vehicles are 0");
                }
                logger.info("vehicles from Beachcomb: {}", String.join(", ", vehicles.stream().map(VehicleDataDTO::toString).toList()));
            } catch (Exception e) {
                logger.error("{}", e.getMessage());
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
