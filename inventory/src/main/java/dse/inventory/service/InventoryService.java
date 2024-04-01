package dse.inventory.service;

import dse.inventory.model.InventoryEntity;
import dse.inventory.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    private final InventoryRepository repository;

    public InventoryService(InventoryRepository repository) {
        this.repository = repository;
    }

    public List<InventoryEntity> findAll() {
        return repository.findAll();
    }

    public InventoryEntity save(InventoryEntity entity) {
        return repository.save(entity);
    }
}
