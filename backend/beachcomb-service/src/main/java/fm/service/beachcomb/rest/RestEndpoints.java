package fm.service.beachcomb.rest;

import fm.api.datafeeder.VehicleDataDTO;
import fm.api.inventory.VehicleType;
import fm.service.beachcomb.mongo.controller.MongoController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:5173")
public class RestEndpoints {
    @Autowired
    FindNearest nearest;
    @Autowired
    MongoController controller;

    @Operation(summary = "Find candidates for matching with the given vehicle")
    @ApiResponse(responseCode = "200", description = "Found successfully",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = VehicleDataDTO.class)))})
    @ApiResponse(responseCode = "400", description = "Invalid vin supplied", content = @Content)
    @ApiResponse(responseCode = "404", description = "Candidates not found", content = @Content)
    @GetMapping(value = "/beachcomb/{vin}")
    @ResponseBody
    public List<VehicleDataDTO> matching(@PathVariable("vin") String vin, @RequestParam("type") VehicleType type) {
        return nearest.findMatches(vin, type);
    }

    @Operation(summary = "Get data of the given vehicle")
    @ApiResponse(responseCode = "200", description = "Found successfully",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = VehicleDataDTO.class))})
    @ApiResponse(responseCode = "400", description = "Invalid vin supplied", content = @Content)
    @ApiResponse(responseCode = "404", description = "Data not found", content = @Content)
    @GetMapping(value = "/beachcomb/vehicles/{vin}")
    public VehicleDataDTO getVehicle(@PathVariable("vin") String vin) {
        return controller.findVehicleByVin(vin);
    }

    @Operation(summary = "Get data of all available vehicles")
    @ApiResponse(responseCode = "200", description = "Found successfully",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = VehicleDataDTO.class)))})
    @ApiResponse(responseCode = "404", description = "Data not found", content = @Content)
    @GetMapping(value = "/beachcomb/vehicles")
    public List<VehicleDataDTO> getAll() {
        return controller.getVehicles();
    }
}
