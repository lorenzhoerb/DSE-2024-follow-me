package fm.service.inventory;

import fm.api.inventory.VehicleType;
import fm.api.inventory.dto.*;
import fm.service.inventory.repository.ManufacturerRepository;
import fm.service.inventory.repository.ModelRepository;
import fm.service.inventory.repository.VehicleRepository;
import fm.service.inventory.service.impl.ManufacturerService;
import fm.service.inventory.service.impl.ModelService;
import fm.service.inventory.service.impl.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class InventoryServiceApplication implements CommandLineRunner {

    @Autowired
    private ManufacturerService manufacturerService;
    @Autowired
    private ModelService modelService;

    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private ModelRepository modelRepository;
    @Autowired
    private ManufacturerRepository manufacturerRepository;
    @Autowired
    private VehicleRepository vehicleRepository;

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Profile("testdata")
    @Override
    public void run(String... args) throws Exception {
        manufacturerRepository.deleteAll();
        modelRepository.deleteAll();
        vehicleRepository.deleteAll();

        ManufacturerRequestDTO manufacturerRequestDTO = new ManufacturerRequestDTO("Mercedes", "MEC");
        ManufacturerDetailsDTO details = manufacturerService.create(manufacturerRequestDTO);

        ModelRequestDTO modelRequestDTO = new ModelRequestDTO("X1", details.getId());
        ModelDetailsDTO model = modelService.create(modelRequestDTO);


        for (int i = 0; i < 2; i++) {
            VehicleType vehicleType = i % 2 == 0 ? VehicleType.FV : VehicleType.LV;
            VehicleRequestDTO vehicleRequestDTO = new VehicleRequestDTO(vehicleType, model.getId());
            vehicleService.create(vehicleRequestDTO);
        }
    }
}
