package org.backend.proyecto.dto;

import org.backend.proyecto.model.TipoProducto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateProductoDTO {

    @Min(1)
    private int codigo;

    @NotBlank
    private String nombre;

    @NotNull
    private TipoProducto tipo;
}

/*
Se tienen tres clases de DTO (CreateProductoDTO, UpdateProductoDTO, ProductoDTO)
para transferir datos entre las capas del sistema y validarlos.
*/