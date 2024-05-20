package fm.service.inventory;

import fm.api.inventory.VehicleType;
import fm.api.inventory.dto.*;
import fm.service.inventory.model.Manufacturer;
import fm.service.inventory.model.Model;
import fm.service.inventory.repository.ManufacturerRepository;
import fm.service.inventory.repository.ModelRepository;
import fm.service.inventory.service.impl.ManufacturerService;
import fm.service.inventory.service.impl.ModelService;
import fm.service.inventory.service.impl.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventoryServiceApplication implements CommandLineRunner {

    @Autowired
    private ManufacturerService manufacturerService;
    @Autowired
    private ModelService modelService;

    @Autowired
    private VehicleService vehicleService;

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ManufacturerRequestDTO manufacturerRequestDTO = new ManufacturerRequestDTO("Mercedes", "MEC");
        ManufacturerDetailsDTO details = manufacturerService.create(manufacturerRequestDTO);

        ModelRequestDTO modelRequestDTO = new ModelRequestDTO("X1", details.getId());
        ModelDetailsDTO model = modelService.create(modelRequestDTO);


        for (int i = 0; i < 10; i++) {
            VehicleType vehicleType = i % 2 == 0 ? VehicleType.FV : VehicleType.LV;
            VehicleRequestDTO vehicleRequestDTO = new VehicleRequestDTO(vehicleType, model.getId());
            vehicleService.create(vehicleRequestDTO);
        }


    }
}
