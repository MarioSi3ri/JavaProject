package org.backend.proyecto.exception;

public class ProductoNotFoundException extends RuntimeException{

    public ProductoNotFoundException(Long id) {
        super("El producto no existe", "ERR_PROD_NOT_FOUND", id);
    }
}
