package dse.inventory.controller;

import dse.inventory.model.InventoryEntity;
import dse.inventory.service.InventoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InventoryController {
    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @GetMapping
    List<InventoryEntity> findAll() {
        // TODO: add mapper for InventoryDto
        return service.findAll();
    }

    @PostMapping
    InventoryEntity save(@RequestBody InventoryEntity entity) {
        // TODO: add mapper for InventoryDto
        return service.save(entity);
    }
}
