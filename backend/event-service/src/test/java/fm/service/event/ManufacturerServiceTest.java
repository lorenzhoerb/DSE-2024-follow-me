package fm.service.event;

import fm.api.common.ConflictException;
import fm.api.inventory.dto.ManufacturerDetailsDTO;
import fm.api.inventory.dto.ManufacturerRequestDTO;
import fm.service.inventory.repository.ManufacturerRepository;
import fm.service.inventory.repository.ModelRepository;
import fm.service.inventory.repository.VehicleRepository;
import fm.service.inventory.service.impl.ManufacturerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ManufacturerServiceTest {

    @Autowired
    private ManufacturerService manufacturerService;
    @Autowired
    private ModelRepository modelRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @BeforeEach
    void setUp() {
        vehicleRepository.deleteAll();
        modelRepository.deleteAll();
        manufacturerRepository.deleteAll();
    }

    @Test
    void testCreateManufacturer_whenValid_expectSuccess() {
        var manufacturerRequestDTO = new ManufacturerRequestDTO(
                "Mercedes",
                "MER"
        );
        ManufacturerDetailsDTO mDetails = manufacturerService.create(manufacturerRequestDTO);
        assertAll(
                () -> assertNotNull(mDetails),
                () -> assertEquals(manufacturerRequestDTO.getManufacturerCode(), mDetails.getManufacturerCode()),
                () -> assertEquals(manufacturerRequestDTO.getManufacturerCode(), mDetails.getManufacturerCode())
        );
    }

    @Test
    void testCreateManufacturer_whenDuplicateManufacturerCod_expectError() {
        var manufacturerRequestDTO = new ManufacturerRequestDTO(
                "Mercedes",
                "MER"
        );
        manufacturerService.create(manufacturerRequestDTO);
        assertThrows(
                ConflictException.class,
                () -> manufacturerService.create(manufacturerRequestDTO));

    }
}
