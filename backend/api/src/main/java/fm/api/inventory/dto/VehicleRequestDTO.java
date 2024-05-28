package fm.api.inventory.dto;

import fm.api.inventory.VehicleType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VehicleRequestDTO {
    @NotNull(message = "type is required")
    private VehicleType type;
    @NotNull(message = "modelId is required")
    private String modelId;
}
