package fm.service.inventory.repository;

import fm.service.inventory.model.Manufacturer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManufacturerRepository extends MongoRepository<Manufacturer, String> {
    Optional<Manufacturer> findByManufacturerCode(String code);
}
