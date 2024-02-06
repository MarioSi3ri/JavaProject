package org.proyecto.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import org.backend.proyecto.dto.PedidoDTO;
import org.backend.proyecto.model.Pedido;
import org.backend.proyecto.repository.PedidoRepository;
import org.backend.proyecto.service.PedidoService;

class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("null")
    @Test
    void testCreatePedido() {
        // Creación de un pedido.
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(new Pedido());

        PedidoDTO pedidoDTO = pedidoService.createPedido();

        assertEquals(1, pedidoDTO.getId());
        assertTrue(pedidoDTO.isActivo());
        assertEquals(0.0, pedidoDTO.getTotal());
    }

}