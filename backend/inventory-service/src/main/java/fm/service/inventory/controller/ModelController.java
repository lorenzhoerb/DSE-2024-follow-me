package fm.service.inventory.controller;

import fm.api.inventory.dto.ModelDetailsDTO;
import fm.api.inventory.dto.ModelRequestDTO;
import fm.service.inventory.service.IModelService;
import fm.service.inventory.service.impl.ModelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("models")
public class ModelController {
    private final IModelService modelService;

    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @Operation(summary = "Create a new model", description = "Creates a new model and returns the created model details")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Model created"),
                    @ApiResponse(responseCode = "400", description = "Validation error"),
                    @ApiResponse(responseCode = "409", description = "Conflict: Manufacturer does not exists"),
            })
    @PostMapping
    ModelDetailsDTO create(@Valid @RequestBody ModelRequestDTO model) {
        return modelService.create(model);
    }
}
