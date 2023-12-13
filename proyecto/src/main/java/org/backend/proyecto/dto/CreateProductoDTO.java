package org.backend.proyecto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.backend.proyecto.model.TipoProducto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateProductoDTO {
    @Schema(description = "Codigo del producto", example = "12")
    @Min(1)
    private int codigo;

    @Schema(description = "Nombre del producto", example = "Papas sabritas adobadas")
    @NotBlank
    private String nombre;

    @Schema(description = "A que sector o área pertenece el producto", example = "COMIDA")
    @NotNull
    private TipoProducto tipo;

    @Schema(description = "Precio en precios mexicanos.", example = "21")
    @NotNull
    private double precio;
}

/*
Se tienen tres clases de DTO (CreateProductoDTO, UpdateProductoDTO, ProductoDTO)
para transferir datos entre las capas del sistema y validarlos.
*/