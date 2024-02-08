package org.backend.proyecto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class PedidoFinalDTO {

    @Schema(description = "Se indica el ID del pedido", example = "2")
    private long id;
    @Schema(description = "Se consulta el precio total del pedido", example = "351.50")
    private double total;
    @Schema(description = "Se consulta la lista de productos del pedido", example = "Sabritas adobadas, Jabón de baño")
    private List<ProductoFinalDTO> productos;
}
