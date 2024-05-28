package fm.service.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "models")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Model {
    @Id
    private String id;
    private String name;
    @DBRef
    private Manufacturer manufacturer;
    @Builder.Default
    private Long vehicleCount = 1L;
}
