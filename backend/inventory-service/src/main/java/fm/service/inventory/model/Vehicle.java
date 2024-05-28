package fm.service.inventory.model;

import fm.api.inventory.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vehicles")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Vehicle {
    @Id
    private String id;

    @Indexed(unique = true)
    private String vin;

    private VehicleType type;

    @DBRef
    private Model model;
}
