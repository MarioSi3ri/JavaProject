package org.backend.proyecto.dto;

import lombok.Data;
import org.backend.proyecto.model.TipoProducto;

@Data
public class UpdateProductoDTO {

    private String nombre;

    private TipoProducto tipo;

    private Double precio;
}