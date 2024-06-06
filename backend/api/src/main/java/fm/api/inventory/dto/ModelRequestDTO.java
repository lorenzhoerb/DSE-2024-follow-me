package fm.api.inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelRequestDTO {
    @NotNull(message = "name is required")
    @NotBlank(message = "name is required")
    @Schema(description = "Name of the model", example = "X1")
    private String name;
    @NotNull(message = "manufacturerId is required")
    @Schema(description = "Id of the manufacturer the model belongs to")
    private String manufacturerId;
}
