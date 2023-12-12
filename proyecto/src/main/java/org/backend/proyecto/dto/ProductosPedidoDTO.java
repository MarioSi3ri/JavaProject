package org.backend.proyecto.dto;

import lombok.Data;

@Data
public class ProductosPedidoDTO {

    private long pedidoId;
    private long productoId;
    private int cantidad;
    private double subTotal;
}
