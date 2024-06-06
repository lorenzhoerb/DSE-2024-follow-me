package fm.service.control.rest;

import fm.api.datafeeder.VehicleDataDTO;
import fm.api.datafeeder.VehicleStatusDTO;
import fm.service.control.mongo.controller.MongoController;
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
    MongoController controller;

    @Operation(summary = "Get status of the given vehicle")
    @ApiResponse(responseCode = "200", description = "Found successfully",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = VehicleStatusDTO.class))})
    @ApiResponse(responseCode = "400", description = "Invalid vin supplied", content = @Content)
    @ApiResponse(responseCode = "404", description = "Status not found", content = @Content)
    @GetMapping(value = "/control/status/{vin}")
    @ResponseBody
    public VehicleStatusDTO getStatus(@PathVariable("vin") String vin) {
        return controller.findByVin(vin);
    }

    @Operation(summary = "Get status of all available vehicles")
    @ApiResponse(responseCode = "200", description = "Found successfully",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = VehicleStatusDTO.class)))})
    @ApiResponse(responseCode = "404", description = "Status not found", content = @Content)
    @GetMapping(value = "/control/status")
    @ResponseBody
    public List<VehicleStatusDTO> getAll() {
        return controller.getStatusList();
    }

}
