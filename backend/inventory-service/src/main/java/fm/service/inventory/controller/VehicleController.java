package fm.service.inventory.controller;

import fm.api.inventory.dto.VehicleDetailsDTO;
import fm.api.inventory.dto.VehicleRequestDTO;
import fm.service.inventory.service.IVehicleService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory/vehicles")
public class VehicleController {

    private final IVehicleService vehicleService;

    public VehicleController(IVehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    VehicleDetailsDTO create(@Valid @RequestBody VehicleRequestDTO vehicle) {
        return vehicleService.create(vehicle);
    }

    @GetMapping("/{vin}")
    VehicleDetailsDTO findByVin(@PathVariable String vin) {
        return vehicleService.findByVin(vin);
    }

    @GetMapping
    List<VehicleDetailsDTO> findAll() {
        return vehicleService.findAll();
    }
}
