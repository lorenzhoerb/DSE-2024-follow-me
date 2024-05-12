package fm.service.inventory;

import fm.service.inventory.model.Manufacturer;
import fm.service.inventory.model.Model;
import fm.service.inventory.repository.ManufacturerRepository;
import fm.service.inventory.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventoryServiceApplication implements CommandLineRunner {

    @Autowired
    private ManufacturerRepository manufacturerRepository;
    @Autowired
    private ModelRepository modelRepository;

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Manufacturer m = new Manufacturer();
        m.setName("Mercedes");
        m.setManufacturerCode("MEC");
        manufacturerRepository.save(m);

        Model model = Model.builder()
                .name("m1")
                .manufacturer(m)
                .vehicleCount(1L)
                .build();
        modelRepository.save(model);
    }
}
