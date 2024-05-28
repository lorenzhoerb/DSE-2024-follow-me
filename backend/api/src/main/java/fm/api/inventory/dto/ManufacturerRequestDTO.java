package fm.api.inventory.dto;

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
    private String name;
    @NotNull(message = "manufacturerCode Is required")
    @Size(min = 3, max = 5, message = "manufacturerCode must be between 3 and 5 characters")
    private String manufacturerCode;
}
