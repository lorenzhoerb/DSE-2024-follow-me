package fm.api.inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManufacturerRequestDTO {
    @NotNull(message = "name is required")
    @NotBlank(message = "name is required")
    @Schema(
            description = "Name of manufacturer",
            example = "Mercedes"
    )
    private String name;
    @NotNull(message = "manufacturerCode Is required")
    @Size(min = 3, max = 5, message = "manufacturerCode must be between 3 and 5 characters")
    @Schema(
            description = "Unique manufacturer coded",
            example = "MEC"
    )
    private String manufacturerCode;
}
