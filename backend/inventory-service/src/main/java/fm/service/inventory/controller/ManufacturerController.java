package fm.service.inventory.controller;

import fm.api.inventory.dto.ManufacturerDetailsDTO;
import fm.api.inventory.dto.ManufacturerRequestDTO;
import fm.service.inventory.service.IManufacturerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("manufacturers")
public class ManufacturerController {

    private final IManufacturerService manufacturerService;

    public ManufacturerController(IManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @Operation(summary = "Get all manufacturers", description = "Returns a list of all manufacturers")
    @ApiResponse(responseCode = "200", description = "Found successfully",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = ManufacturerDetailsDTO.class)))})
    @GetMapping
    public List<ManufacturerDetailsDTO> findAll() {
        return manufacturerService.findAll();
    }

    @Operation(summary = "Create a new manufacturer", description = "Creates a new manufacturer and returns the created manufacturer details")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Manufacturer created"),
                    @ApiResponse(responseCode = "400", description = "Validation error"),
                    @ApiResponse(responseCode = "409", description = "Conflict: Manufacturer with manufacturer code already exists"),
            })
    @PostMapping
    public ManufacturerDetailsDTO createManufacturer(@Valid @RequestBody ManufacturerRequestDTO manufacturer) {
        return manufacturerService.create(manufacturer);
    }

}
