package org.backend.proyecto.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;
import org.backend.proyecto.model.Producto;
import org.backend.proyecto.model.TipoProducto;
import org.backend.proyecto.repository.ProductoRepository;

@DataJpaTest
class ProductoRepositoryTest {

    @Autowired
    private ProductoRepository productoRepository;

    @Test
    void testSaveProducto() {
        // Guardar un Producto en la base de datos y luego recuperarlo.
        Producto producto = new Producto(1, "Producto1", TipoProducto.COMIDA, 10.0);
        producto.setId(1L);
        productoRepository.save(producto);

        Producto result = productoRepository.findById(1L).orElse(null);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Producto1", result.getNombre());
        assertEquals(TipoProducto.COMIDA, result.getTipo());
        assertEquals(10.0, result.getPrecio());
    }
    
}