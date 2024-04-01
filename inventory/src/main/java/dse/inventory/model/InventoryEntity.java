package dse.inventory.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("inventory")
public class InventoryEntity {
    @Id
    String oem;
    String modeltype;
    String vin;
}
