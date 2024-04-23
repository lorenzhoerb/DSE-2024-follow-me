package fm.api.inventory.dto;

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
    private String name;
    @NotNull(message = "manufacturerId is required")
    private Long manufacturerId;
}
