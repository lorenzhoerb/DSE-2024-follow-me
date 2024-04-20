package dse.inventory.controller;

import dse.inventory.model.InventoryEntity;
import dse.inventory.service.InventoryService;
import dse.inventory.service.RabbitMqTestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InventoryController {
    private final InventoryService service;
    private final RabbitMqTestService rabbitMqTestService;

    public InventoryController(InventoryService service, RabbitMqTestService rabbitMqTestService) {
        this.service = service;
        this.rabbitMqTestService = rabbitMqTestService;
    }

    @GetMapping
    List<InventoryEntity> findAll() {
        rabbitMqTestService.send("Executing findAll...");
        // TODO: add mapper for InventoryDto
        return service.findAll();
    }

    @PostMapping
    InventoryEntity save(@RequestBody InventoryEntity entity) {
        rabbitMqTestService.send("Executing save...");
        // TODO: add mapper for InventoryDto
        return service.save(entity);
    }
}
