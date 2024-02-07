package org.backend.proyecto.service;

import org.backend.proyecto.dto.AddProductoDTO;
import org.backend.proyecto.dto.ProductosPedidoDTO;
import org.backend.proyecto.exception.PedidoNotFoundException;
import org.backend.proyecto.exception.ProductoNotFoundException;
import org.backend.proyecto.mapper.ProductosPedidoMapper;
import org.backend.proyecto.model.Pedido;
import org.backend.proyecto.model.Producto;
import org.backend.proyecto.model.ProductosPedido;
import org.backend.proyecto.repository.ProductosPedidoRepository;
import org.backend.proyecto.service.PedidoService;
import org.backend.proyecto.service.ProductoService;
import org.backend.proyecto.service.ProductosPedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductosPedidoServiceTest {

    @Mock
    private ProductosPedidoRepository productosPedidoRepository;

    @Mock
    private ProductoService productoService;

    @Mock
    private PedidoService pedidoService;

    @Mock
    private ProductosPedidoMapper productosPedidoMapper;

    @InjectMocks
    private ProductosPedidoService productosPedidoService;

    @SuppressWarnings("deprecation")
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @SuppressWarnings("null")
    @Test
    void testAddProducto() throws PedidoNotFoundException, ProductoNotFoundException {
        // Arrange
        AddProductoDTO dto = new AddProductoDTO();
        dto.setId(1L);
        dto.setCantidad(5);

        Pedido pedido = new Pedido();
        pedido.setId(1L);

        Producto producto = new Producto(0, null, null, 0);
        producto.setId(1L);
        producto.setPrecio(10.0);

        double subTotal = producto.getPrecio() * dto.getCantidad();

        ProductosPedido productosPedido = new ProductosPedido();
        productosPedido.setId(1L);
        productosPedido.setPedido(pedido);
        productosPedido.setProducto(producto);
        productosPedido.setCantidad(dto.getCantidad());
        productosPedido.setSubTotal(subTotal);

        when(pedidoService.findActivo()).thenReturn(pedido);
        when(productoService.findProduct(dto.getId())).thenReturn(producto);
        when(productosPedidoMapper.toModel(pedido.getId(), producto.getId(), dto.getCantidad(), subTotal)).thenReturn(productosPedido);
        when(productosPedidoRepository.save(any())).thenReturn(productosPedido);

        // Act
        ProductosPedidoDTO result = productosPedidoService.addProducto(dto);

        // Assert
        assertNotNull(result);
        assertEquals(productosPedido.getId(), result.getId());
        assertEquals(productosPedido.getPedido().getId(), result.getPedidoId());
        assertEquals(productosPedido.getProducto().getId(), result.getProductoId());
        assertEquals(productosPedido.getCantidad(), result.getCantidad());
        assertEquals(productosPedido.getSubTotal(), result.getSubTotal());
    }

}