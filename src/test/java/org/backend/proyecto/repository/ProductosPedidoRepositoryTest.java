package org.backend.proyecto.repository;

import org.backend.proyecto.model.Pedido;
import org.backend.proyecto.model.Producto;
import org.backend.proyecto.model.ProductosPedido;
import org.backend.proyecto.model.TipoProducto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@DataJpaTest
class ProductosPedidoRepositoryTest {

    @Autowired
    private ProductosPedidoRepository repository;

    @Autowired
    private TestEntityManager manager;

    @Test
    @DisplayName("Repository should be injected")
    void smokeTest() {
        assertNotNull(repository);
    }

    @Test
    @DisplayName("Repository should save a ProductosPedido")
    void saveProductoPedidoTest() {
        // Guardar un ProductosPedido en la base de datos y luego recuperarlo.
        Pedido pedido = new Pedido();
        pedido.setId(1L);

        Producto producto = new Producto(1, "Producto", TipoProducto.ROPA, 105.25);
        producto.setId(1L);

        ProductosPedido productosPedido = new ProductosPedido(pedido, producto, 3, producto.getPrecio() * 3);

        repository.save(productosPedido);

        ProductosPedido result = repository.findAll().get(0);

        assertNotNull(result);
        assertTrue(result.getId() > 0);
        assertEquals(pedido.getId(), result.getPedido().getId());
        assertEquals(producto.getId(), result.getProducto().getId());
        assertEquals(3, result.getCantidad());
        assertEquals(productosPedido.getSubTotal(), result.getSubTotal());
    }

    @Test
    @DisplayName("Repository should find if exists by product id")
    void existByProductIdTest() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);

        Producto producto = new Producto(1, "Producto", TipoProducto.ROPA, 105.25);
        producto.setId(1L);

        ProductosPedido productosPedido = new ProductosPedido(pedido, producto, 3, producto.getPrecio() * 3);

        manager.persist(productosPedido);

        boolean result = repository.existsByProductoId(producto.getId());

        assertTrue(result);
    }

    @Test
    @DisplayName("Repository should filter by pedido id")
    void findByPedidoIdTest() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);

        Producto producto1 = new Producto(1, "Producto", TipoProducto.ROPA, 105.25);
        Producto producto2 = new Producto(2, "Producto", TipoProducto.LIBROS, 85.36);

        producto1.setId(1L);
        producto2.setId(2L);

        ProductosPedido productosPedido1 = new ProductosPedido(pedido, producto1, 3, producto1.getPrecio() * 3);
        ProductosPedido productosPedido2 = new ProductosPedido(pedido, producto2, 8, producto2.getPrecio() * 8);

        manager.persist(productosPedido1);
        manager.persist(productosPedido2);

        List<ProductosPedido> result = repository.findByPedidoId(pedido.getId()).get();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(pedido.getId(), result.get(0).getPedido().getId());
        assertEquals(pedido.getId(), result.get(1).getPedido().getId());

        assertEquals(producto1.getId(), result.get(0).getProducto().getId());
        assertEquals(producto2.getId(), result.get(1).getProducto().getId());
    }

    @Test
    @DisplayName("Repository should ")
    void findSumSubTotalByPedidoIdTest() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);

        Producto producto1 = new Producto(1, "Producto", TipoProducto.ROPA, 105.25);
        Producto producto2 = new Producto(2, "Producto", TipoProducto.LIBROS, 85.36);

        producto1.setId(1L);
        producto2.setId(2L);

        ProductosPedido productosPedido1 = new ProductosPedido(pedido, producto1, 3, producto1.getPrecio() * 3);
        ProductosPedido productosPedido2 = new ProductosPedido(pedido, producto2, 8, producto2.getPrecio() * 8);

        manager.persist(productosPedido1);
        manager.persist(productosPedido2);

        double result = repository.findSumSubTotalByPedidoId(pedido.getId());

        assertEquals(productosPedido1.getSubTotal() + productosPedido2.getSubTotal(), result);
    }

    @Test
    @DisplayName("Repository should delete a productPedido")
    void deleteByProductoIdTest() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);

        Producto producto = new Producto(1, "Producto", TipoProducto.ROPA, 105.25);
        producto.setId(1L);

        ProductosPedido productosPedido = new ProductosPedido(pedido, producto, 3, producto.getPrecio() * 3);

        manager.persist(productosPedido);

        repository.deleteByProductoId(producto.getId());

        List<ProductosPedido> result = repository.findAll();

        assertTrue(result.isEmpty());
    }
}