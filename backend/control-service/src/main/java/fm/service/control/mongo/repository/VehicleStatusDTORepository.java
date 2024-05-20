package fm.service.control.mongo.repository;

import fm.api.datafeeder.VehicleStatusDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VehicleStatusDTORepository extends MongoRepository<VehicleStatusDTO,String> {
}
