package org.backend.proyecto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AddProductoDTO {
    @Schema(description = "Id del producto", example = "21")
    @NotNull
    private Long id;

    @Schema(description = "Cantidad (en enteros)", example = "15")
    @NotNull
    private Integer cantidad;
}
