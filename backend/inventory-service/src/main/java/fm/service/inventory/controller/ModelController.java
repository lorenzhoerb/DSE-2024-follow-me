package fm.service.inventory.controller;

import fm.api.inventory.dto.ModelDetailsDTO;
import fm.api.inventory.dto.ModelRequestDTO;
import fm.service.inventory.service.IModelService;
import fm.service.inventory.service.impl.ModelService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory/models")
public class ModelController {
    private final IModelService modelService;

    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @PostMapping
    ModelDetailsDTO create(@Valid @RequestBody ModelRequestDTO model) {
        return modelService.create(model);
    }
}
