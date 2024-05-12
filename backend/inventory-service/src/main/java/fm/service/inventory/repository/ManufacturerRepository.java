package fm.service.inventory.repository;

import fm.service.inventory.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    Optional<Manufacturer> findByManufacturerCode(String code);
}
