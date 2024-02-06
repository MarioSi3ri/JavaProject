package org.proyecto.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import org.backend.proyecto.controller.ProductosPedidoController;
import org.backend.proyecto.dto.AddProductoDTO;
import org.backend.proyecto.dto.ProductosPedidoDTO;
import org.backend.proyecto.exception.PedidoNotFoundException;
import org.backend.proyecto.exception.ProductoNotFoundException;
import org.backend.proyecto.service.ProductosPedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

class ProductosPedidoControllerTest {

    @Mock
    private ProductosPedidoService productosPedidoService;

    @InjectMocks
    private ProductosPedidoController productosPedidoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Controller should save a product")
    void testAddProducto() throws PedidoNotFoundException, ProductoNotFoundException {
        // Adición de un producto a un pedido.
        AddProductoDTO addProductoDTO = new AddProductoDTO();
        ProductosPedidoDTO productosPedidoDTO = new ProductosPedidoDTO();

        when(productosPedidoService.addProducto(addProductoDTO)).thenReturn(productosPedidoDTO);

        ProductosPedidoDTO response = productosPedidoController.addProducto(addProductoDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, ((ProductosPedidoDTO) response.getBody()).getPedidoId());
        assertEquals(3, ((AddProductoDTO) response.getBody()).getCantidad());
        assertEquals(30.0, ((ProductosPedidoDTO) response.getBody()).getSubTotal());
    }
}
