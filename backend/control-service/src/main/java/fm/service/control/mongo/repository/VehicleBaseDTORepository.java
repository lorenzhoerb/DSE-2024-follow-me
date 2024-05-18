package fm.service.control.mongo.repository;

import fm.api.inventory.dto.VehicleBaseDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VehicleBaseDTORepository extends MongoRepository<VehicleBaseDTO, String> {
}
