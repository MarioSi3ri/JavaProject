package org.backend.proyecto.controller;

import org.backend.proyecto.exception.PedidoNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import org.backend.proyecto.dto.PedidoDTO;
import org.backend.proyecto.service.PedidoService;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class PedidoControllerTest {

    @MockBean
    private PedidoService service;

    @Autowired
    private PedidoController controller;

    @Test
    @DisplayName("Controller should be injected")
    void smokeTest() {
        assertNotNull(controller);
    }

    @Test
    @DisplayName("Controller should save an order")
    void createPedidoTest() throws Exception {
        PedidoDTO dto = new PedidoDTO(1L, true, 0.0);

        when(service.createPedido()).thenReturn(dto);

        PedidoDTO result = controller.createPedido();

        assertNotNull(result);
        assertEquals(dto.getId(), result.getId());
        assertTrue(result.isActivo());
        assertEquals(0.0, result.getTotal());
    }

    @Test
    @DisplayName("Controller should return a list of orders")
    void getPedidosTest() {
        PedidoDTO dto1 = new PedidoDTO(1L, true, 0.0);
        PedidoDTO dto2 = new PedidoDTO(2L, true, 0.0);

        when(service.getAll()).thenReturn(Arrays.asList(dto1, dto2));

        List<PedidoDTO> result = controller.getPedidos();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(dto1.getId(), result.get(0).getId());
        assertEquals(dto2.getId(), result.get(1).getId());
    }

    @Test
    @DisplayName("Controller should return a pedido")
    void getPedidoTest() throws PedidoNotFoundException {
        PedidoDTO dto = new PedidoDTO(1L, true, 0.0);

        when(service.getPedido(anyLong())).thenReturn(dto);

        PedidoDTO result = controller.getPedido(1L);
        assertEquals(dto.getId(), result.getId());
        assertTrue(result.isActivo());
        assertEquals(0.0, result.getTotal());
    }

    @Test
    @DisplayName("Controller should throw an error if pedido was not found")
    void getPedidoWithErrorTest() throws PedidoNotFoundException {
        when(service.getPedido(anyLong())).thenThrow(PedidoNotFoundException.class);

        assertThrows(PedidoNotFoundException.class, () -> controller.getPedido(1L));
    }
}