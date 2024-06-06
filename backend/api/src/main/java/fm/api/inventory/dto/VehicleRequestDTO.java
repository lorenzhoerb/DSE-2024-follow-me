package fm.api.inventory.dto;

import fm.api.inventory.VehicleType;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Vehicle type")
    private VehicleType type;
    @NotNull(message = "modelId is required")
    @Schema(description = "the model of the car")
    private String modelId;
}
