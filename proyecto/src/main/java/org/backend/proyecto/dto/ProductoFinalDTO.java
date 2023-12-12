package org.backend.proyecto.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductoFinalDTO {
    private String nombre;
    private double precio;
    private int cantidad;
}
