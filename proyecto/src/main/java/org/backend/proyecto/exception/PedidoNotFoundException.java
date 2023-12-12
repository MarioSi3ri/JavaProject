package org.backend.proyecto.exception;

public class PedidoNotFoundException extends RuntimeException{

    public PedidoNotFoundException(Long id) {
        super("El pedido no existe", "ERR_PED_NOT_FOUND", id);
    }
}
