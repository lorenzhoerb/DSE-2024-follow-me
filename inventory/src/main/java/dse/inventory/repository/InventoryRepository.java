package dse.inventory.repository;

import dse.inventory.model.InventoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends MongoRepository<InventoryEntity, String> {
}
