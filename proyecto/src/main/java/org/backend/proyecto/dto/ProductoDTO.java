package org.backend.proyecto.dto;

import org.backend.proyecto.model.TipoProducto;
import lombok.Data;

@Data
public class ProductoDTO {

    private long id;
    private int codigo;
    private String nombre;
    private TipoProducto tipo;
}