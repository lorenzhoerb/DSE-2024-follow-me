package fm.service.inventory.controller;

import fm.api.inventory.dto.VehicleDetailsDTO;
import fm.api.inventory.dto.VehicleRequestDTO;
import fm.service.inventory.service.IVehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("vehicles")
public class VehicleController {

    private final IVehicleService vehicleService;

    public VehicleController(IVehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Operation(summary = "Create a new vehicle", description = "Creates a new vehicle and returns the created vehicle details")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Vehicle created"),
                    @ApiResponse(responseCode = "400", description = "Validation error"),
                    @ApiResponse(responseCode = "409", description = "Conflict: Model does not exists"),
            })
    @PostMapping
    VehicleDetailsDTO create(@Valid @RequestBody VehicleRequestDTO vehicle) {
        return vehicleService.create(vehicle);
    }

    @Operation(summary = "Find vehicle by VIN", description = "Retrieves the details of a vehicle by its VIN")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Vehicle found"),
                    @ApiResponse(responseCode = "404", description = "Vehicle not found")
            })
    @GetMapping("/{vin}")
    VehicleDetailsDTO findByVin(@PathVariable String vin) {
        return vehicleService.findByVin(vin);
    }

    @Operation(summary = "Find all vehicles", description = "Retrieves the list of all vehicles")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Vehicles found")
            })

    @GetMapping
    List<VehicleDetailsDTO> findAll() {
        return vehicleService.findAll();
    }
}
