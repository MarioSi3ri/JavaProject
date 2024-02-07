package org.backend.proyecto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.backend.proyecto.model.TipoProducto;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {

    @Schema(description = "Se ingresa el ID del producto", example = "3")
    private long id;
    @Schema(description = "Se ingresa el codigo del producto", example = "12")
    private int codigo;
    @Schema(description = "Se ingresa el nombre del producto", example = "Sabritas adobadas")
    private String nombre;
    @Schema(description = "Se ingresa a que sector pertenece", example = "COMIDA")
    private TipoProducto tipo;
    @Schema(description = "Se ingresa el precio del producto", example = "23")
    private double precio;
}