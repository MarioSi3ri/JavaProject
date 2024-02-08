package org.backend.proyecto.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import org.backend.proyecto.dto.AddProductoDTO;
import org.backend.proyecto.dto.ProductoFinalDTO;
import org.backend.proyecto.dto.ProductosPedidoDTO;
import org.backend.proyecto.exception.PedidoNotFoundException;
import org.backend.proyecto.exception.ProductoNotFoundException;
import org.backend.proyecto.service.ProductosPedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ProductosPedidoControllerTest {

    @MockBean
    private ProductosPedidoService service;

    @Autowired
    private ProductosPedidoController controller;

    @Test
    @DisplayName("Controller should be injected")
    void smokeTest() {
        assertNotNull(controller);
    }

    @Test
    @DisplayName("Controller should save a product")
    void addProductoTest() throws PedidoNotFoundException, ProductoNotFoundException {
        AddProductoDTO data = new AddProductoDTO();
        data.setId(8L);
        data.setCantidad(52);

        ProductosPedidoDTO dto = new ProductosPedidoDTO();
        dto.setPedidoId(1L);
        dto.setProductoId(8L);
        dto.setCantidad(52);
        dto.setSubTotal(data.getCantidad() * 55.22);

        when(service.addProducto(any(AddProductoDTO.class))).thenReturn(dto);

        ProductosPedidoDTO result = controller.addProducto(data);

        assertNotNull(result);
        assertEquals(1L, result.getPedidoId());
        assertEquals(data.getId(), result.getProductoId());
        assertEquals(data.getCantidad(), result.getCantidad());
        assertEquals(dto.getSubTotal(), result.getSubTotal());
    }

    @Test
    @DisplayName("Controller should remove a product of a pedido")
    void removeProductoTest() throws ProductoNotFoundException {
        controller.removeProducto(546L);

        verify(service, times(1)).removeProducto(546L);
    }

    @Test
    @DisplayName("Controller should deactivate a pedido")
    void finalizarPedidoTest() throws PedidoNotFoundException {
        controller.finalizarPedido();

        verify(service, times(1)).closePedido();
    }

    @Test
    @DisplayName("Controller should return a list of the products of a pedido")
    void getProductosPedidoTest() throws PedidoNotFoundException {
        ProductoFinalDTO dto = new ProductoFinalDTO();
        dto.setNombre("Producto");
        dto.setCantidad(88);
        dto.setPrecio(452.02);

        when(service.getProductosPorPedido(anyLong())).thenReturn(List.of(dto));

        List<ProductoFinalDTO> result = controller.getProductosPedido(8L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(dto.getNombre(), result.get(0).getNombre());
        assertEquals(dto.getCantidad(), result.get(0).getCantidad());
        assertEquals(dto.getPrecio(), result.get(0).getPrecio());
    }
}
