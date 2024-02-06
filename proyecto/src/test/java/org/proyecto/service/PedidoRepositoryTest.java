package org.proyecto.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;
import org.backend.proyecto.model.Pedido;
import org.backend.proyecto.repository.PedidoRepository;

@DataJpaTest
class PedidoRepositoryTest {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Test
    void testSavePedido() {
        // Guardar un Pedido en la base de datos y luego recuperarlo.
        Pedido pedido = new Pedido(1, true, 0.0);
        pedidoRepository.save(pedido);

        Pedido result = pedidoRepository.findById(1L).orElse(null);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertTrue(result.isActivo());
        assertEquals(0.0, result.getTotal());
    }

}