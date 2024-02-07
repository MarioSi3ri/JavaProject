package org.backend.proyecto.service;

import org.backend.proyecto.exception.PedidoNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.backend.proyecto.dto.PedidoDTO;
import org.backend.proyecto.model.Pedido;
import org.backend.proyecto.repository.PedidoRepository;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class PedidoServiceTest {

    @MockBean
    private PedidoRepository repository;

    @Autowired
    private PedidoService service;

    @Test
    @DisplayName("Repository should be injected")
    void smokeTest() {
        assertNotNull(repository);
    }

    @Test
    @DisplayName("Service should save an order in repository")
    void createPedidoTest() {
        when(repository.save(any(Pedido.class))).thenReturn(new Pedido());

        PedidoDTO result = service.createPedido();

        assertNotNull(result);
        assertTrue(result.isActivo());
        assertEquals(0.0, result.getTotal());
    }

    @Test
    @DisplayName("Service should return orders from repository")
    void getAllTest() {
        Pedido pedido1 = new Pedido();
        Pedido pedido2 = new Pedido();
        Pedido pedido3 = new Pedido();

        when(repository.findAll()).thenReturn(Arrays.asList(pedido1, pedido2, pedido3));

        List<PedidoDTO> result = service.getAll();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(pedido1.getId(), result.get(0).getId());
        assertEquals(pedido2.getId(), result.get(1).getId());
        assertEquals(pedido3.getId(), result.get(2).getId());
    }

    @Test
    @DisplayName("Service should return active order from repository")
    void findActiveTest() throws PedidoNotFoundException {
        Pedido pedido = new Pedido();

        when(repository.findByActivoTrue()).thenReturn(Optional.of(pedido));

        Pedido result = service.findActivo();

        assertNotNull(result);
        assertEquals(pedido.getId(), result.getId());
        assertTrue(result.isActivo());
    }

    @Test
    @DisplayName("Service should return a specific order")
    void getPedidoTest() throws PedidoNotFoundException {
        Pedido pedido = new Pedido();

        when(repository.findById(anyLong())).thenReturn(Optional.of(pedido));

        PedidoDTO result = service.getPedido(pedido.getId());

        assertNotNull(result);
        assertEquals(pedido.getId(), result.getId());
        assertTrue(result.isActivo());
        assertEquals(0.0, result.getTotal());
    }

    @Test
    @DisplayName("Service should throw an error if order was not found")
    void findPedidoWithErrorTest() {
        Optional<Pedido> dummy = Optional.empty();

        when(repository.findById(anyLong())).thenReturn(dummy);

        assertThrows(PedidoNotFoundException.class, () -> service.getPedido(10));
    }

    @Test
    @DisplayName("Service should throw an error if order was not found")
    void findActivoWithErrorTest() {
        Optional<Pedido> dummy = Optional.empty();

        when(repository.findById(anyLong())).thenReturn(dummy);

        assertThrows(PedidoNotFoundException.class, () -> service.findActivo());
    }
}














