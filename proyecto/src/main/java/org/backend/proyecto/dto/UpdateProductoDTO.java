package org.backend.proyecto.dto;

import org.backend.proyecto.model.TipoProducto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateProductoDTO {

    private String nombre;

    private TipoProducto tipo;

    private Double precio;
}