package org.backend.proyecto.dto;

import lombok.Data;

import java.util.List;

@Data
public class PedidoFinalDTO {

    private long id;
    private double total;
    private List<ProductoFinalDTO> productos;
}
