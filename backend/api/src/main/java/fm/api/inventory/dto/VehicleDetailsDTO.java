package fm.api.inventory.dto;

import fm.api.inventory.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VehicleDetailsDTO {
    private String vin;
    private VehicleType type;
    private Long modelId;
    private String manufacturer;
    private String manufacturerCode;
    private String model;
}
