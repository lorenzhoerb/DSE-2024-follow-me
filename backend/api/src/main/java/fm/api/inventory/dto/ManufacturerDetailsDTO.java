package fm.api.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ManufacturerDetailsDTO {
    private Long id;
    private String name;
    private String manufacturerCode;
}
