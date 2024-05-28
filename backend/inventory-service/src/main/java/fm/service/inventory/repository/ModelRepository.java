package fm.service.inventory.repository;

import fm.service.inventory.model.Model;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface ModelRepository extends MongoRepository<Model, String> {
}
