package org.backend.proyecto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PedidoDTO {

    @Schema(description = "Numero de ID del pedido", example = "2")
    private long id;

    @Schema(description = "Indica si el pedido esta activo", example = "true")
    private boolean activo;
    @Schema(description = "Total en pesos mexicanos del pedido", example = "325.23")
    private double total;
}
