package org.backend.proyecto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.backend.proyecto.model.TipoProducto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductoDTO {

    @Schema(description = "Nombre actualizado del producto", example = "Sabritas Flamming Hot")
    private String nombre;

    @Schema(description = "Tipo de producto actulizado", example = "ELECTRONICA")
    private TipoProducto tipo;

    @Schema(description = "Precio actializado del producto", example = "25")
    private Double precio;
}