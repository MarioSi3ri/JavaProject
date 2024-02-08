package org.backend.proyecto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProductosPedidoDTO {

    @Schema(description = "ID del pedido", example = "3")
    private long pedidoId;
    @Schema(description = "ID del producto", example = "12")
    private long productoId;
    @Schema(description = "Cantidad del producto", example = "3")
    private int cantidad;
    @Schema(description = "Subtotal del pedido", example = "300")
    private double subTotal;

}
