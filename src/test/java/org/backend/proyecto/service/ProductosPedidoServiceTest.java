package org.backend.proyecto.service;

import org.backend.proyecto.dto.AddProductoDTO;
import org.backend.proyecto.dto.PedidoFinalDTO;
import org.backend.proyecto.dto.ProductoFinalDTO;
import org.backend.proyecto.dto.ProductosPedidoDTO;
import org.backend.proyecto.exception.PedidoNotFoundException;
import org.backend.proyecto.exception.ProductoNotFoundException;
import org.backend.proyecto.model.Pedido;
import org.backend.proyecto.model.Producto;
import org.backend.proyecto.model.ProductosPedido;
import org.backend.proyecto.model.TipoProducto;
import org.backend.proyecto.repository.ProductosPedidoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ProductosPedidoServiceTest {

    @MockBean
    private ProductosPedidoRepository repository;

    @MockBean
    private ProductoService productoService;

    @MockBean
    private PedidoService pedidoService;

    @Autowired
    private ProductosPedidoService service;

    @Test
    @DisplayName("Service should be injected")
    void smokeTest() {
        assertNotNull(service);
    }

    @Test
    @DisplayName("Service should add a product in an order in repository")
    void addProductoTest() throws PedidoNotFoundException, ProductoNotFoundException {
        // Arrange
        Pedido pedido = new Pedido();
        pedido.setId(1L);

        Producto producto = new Producto(1, "Producto", TipoProducto.ROPA, 105.25);
        producto.setId(1L);

        AddProductoDTO dto = new AddProductoDTO();
        dto.setId(1L);
        dto.setCantidad(5);

        ProductosPedido productosPedido = new ProductosPedido(pedido, producto,dto.getCantidad(), producto.getPrecio() * dto.getCantidad());

        when(pedidoService.findActivo()).thenReturn(pedido);
        when(productoService.findProduct(anyLong())).thenReturn(producto);
        when(repository.save(any(ProductosPedido.class))).thenReturn(productosPedido);

        // Act
        ProductosPedidoDTO result = service.addProducto(dto);

        // Assert
        assertNotNull(pedidoService.findActivo());
        assertNotNull(productoService.findProduct(producto.getId()));

        assertNotNull(result);
        assertTrue(pedido.getId() >= 1);
        assertEquals(pedido.getId(), result.getPedidoId());
        assertEquals(producto.getId(), result.getProductoId());
        assertEquals(dto.getCantidad(), result.getCantidad());
        assertEquals(productosPedido.getSubTotal(), result.getSubTotal());
    }

    @Test
    @DisplayName("Service should return the products of an order from repository")
    void getProductsPerOrderTest() throws PedidoNotFoundException {
        Pedido pedido = new Pedido();
        pedido.setId(1L);

        Producto producto1 = new Producto(1, "Producto", TipoProducto.ROPA, 105.25);
        Producto producto2 = new Producto(2, "Producto", TipoProducto.LIBROS, 85.36);

        producto1.setId(1L);
        producto2.setId(2L);

        ProductosPedido productosPedido1 = new ProductosPedido(pedido, producto1, 3, producto1.getPrecio() * 3);
        ProductosPedido productosPedido2 = new ProductosPedido(pedido, producto2, 8, producto2.getPrecio() * 8);

        when(repository.findByPedidoId(anyLong())).thenReturn(Optional.of(Arrays.asList(productosPedido1, productosPedido2)));

        List<ProductoFinalDTO> result = service.getProductosPorPedido(pedido.getId());

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(producto1.getNombre(), result.get(0).getNombre());
        assertEquals(producto2.getNombre(), result.get(1).getNombre());
    }

    @Test
    @DisplayName("Service should remove a product from an order in repository")
    void removeProductTest() throws ProductoNotFoundException {
        when(repository.existsByProductoId(anyLong())).thenReturn(true);

        service.removeProducto(66566L);

        verify(repository, times(1)).deleteByProductoId(66566L);
    }

    @Test
    @DisplayName("Service should deactivate an order")
    void closePedidoTest() throws PedidoNotFoundException {
        Pedido pedido = new Pedido();
        pedido.setId(1L);

        Producto producto = new Producto(1, "Producto", TipoProducto.ROPA, 105.25);
        producto.setId(1L);

        ProductosPedido productosPedido = new ProductosPedido(pedido, producto,4, producto.getPrecio() * 4);

        when(pedidoService.findActivo()).thenReturn(pedido);
        when(repository.findSumSubTotalByPedidoId(anyLong())).thenReturn(productosPedido.getSubTotal());
        when(repository.findByPedidoId(anyLong())).thenReturn(Optional.of(List.of(productosPedido)));

        PedidoFinalDTO result = service.closePedido();

        assertNotNull(result);
        assertFalse(pedido.isActivo());
        assertEquals(pedido.getId(), result.getId());
        assertEquals(productosPedido.getSubTotal(), result.getTotal());
        assertEquals(1, result.getProductos().size());
    }
}