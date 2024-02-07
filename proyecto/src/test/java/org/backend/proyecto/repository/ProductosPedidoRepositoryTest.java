package org.backend.proyecto.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.backend.proyecto.repository.ProductosPedidoRepository;

@DataJpaTest
class ProductosPedidoRepositoryTest {

    @Autowired
    private ProductosPedidoRepository productosPedidoRepository;

//    @Test
//    void testSaveProductosPedido() {
//        // Guardar un ProductosPedido en la base de datos y luego recuperarlo.
//        ProductosPedido productosPedido = new ProductosPedido(1, 1, 3, 30.0);
//        productosPedidoRepository.save(productosPedido);
//
//        ProductosPedido result = productosPedidoRepository.findById(1L).orElse(null);
//
//        assertNotNull(result);
//        assertEquals(1, result.getId());
//        assertEquals(1, result.getPedido());
//        assertEquals(3, result.getCantidad());
//        assertEquals(30.0, result.getSubTotal());
//    }

}