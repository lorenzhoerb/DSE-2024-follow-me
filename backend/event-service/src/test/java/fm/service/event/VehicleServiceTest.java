package fm.service.event;


import fm.service.inventory.service.impl.VehicleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VehicleServiceTest {

    @Autowired
    private final VehicleService vehicleService;

    public VehicleServiceTest(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Test
    void testCreateVehicle_withValidData_expectSuccess() {

    }
}
