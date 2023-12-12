package org.backend.proyecto.dto;

import lombok.Data;

@Data
public class PedidoDTO {

    private long id;
    private boolean activo;
    private double total;
}
