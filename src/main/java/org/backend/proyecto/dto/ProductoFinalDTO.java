package org.backend.proyecto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductoFinalDTO {
    @Schema(description = "Nombre del producto", example = "Sabritas adobadas")
    private String nombre;
    @Schema(description = "Precio del producto", example = "21")
    private double precio;
    @Schema(description = "Cantidad del producto", example = "3")
    private int cantidad;
}
