package fm.service.inventory.repository;

import fm.service.inventory.model.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends MongoRepository<Vehicle, Long> {
    Optional<Vehicle> findVehicleByVin(String vin);
}
