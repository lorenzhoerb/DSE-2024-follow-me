package fm.service.control.mongo.repository;

import fm.api.datafeeder.VehicleDataDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VehicleDataDTORepository extends MongoRepository<VehicleDataDTO, String> {
}
