package fm.service.inventory.controller;

import fm.api.inventory.dto.ManufacturerDetailsDTO;
import fm.api.inventory.dto.ManufacturerRequestDTO;
import fm.service.inventory.service.IManufacturerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory/manufacturers")
public class ManufacturerController {

    private final IManufacturerService manufacturerService;

    public ManufacturerController(IManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public List<ManufacturerDetailsDTO> findAll() {
        return manufacturerService.findAll();
    }

    @PostMapping
    public ManufacturerDetailsDTO createManufacturer(@Valid @RequestBody ManufacturerRequestDTO manufacturer) {
        return manufacturerService.create(manufacturer);
    }

}
