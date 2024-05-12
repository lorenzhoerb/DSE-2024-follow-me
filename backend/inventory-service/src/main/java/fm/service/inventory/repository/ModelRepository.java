package fm.service.inventory.repository;

import fm.service.inventory.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface ModelRepository extends JpaRepository<Model, Long> {
}
