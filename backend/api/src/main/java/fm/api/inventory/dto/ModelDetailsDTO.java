package fm.api.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelDetailsDTO {
    private String id;
    private String name;
    private String manufacturerId;
}
