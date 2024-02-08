package org.backend.proyecto.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;
import org.backend.proyecto.model.Producto;
import org.backend.proyecto.model.TipoProducto;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@ExtendWith(SpringExtension.class)
@DataJpaTest
class ProductoRepositoryTest {

    @Autowired
    private ProductoRepository repository;

    @Autowired
    private TestEntityManager manager;

    @Test
    @DisplayName("Repository should be injected")
    void smokeTest() {
        assertNotNull(repository);
    }

    @Test
    @DisplayName("Repository should save a product")
    void saveProductTest() {
        // Guardar un Producto en la base de datos y luego recuperarlo.
        Producto producto = new Producto(1, "Producto", TipoProducto.COMIDA, 10.0);

        repository.save(producto);

        Producto result = repository.findAll().get(0);

        assertNotNull(result);
        assertEquals(producto.getId(), result.getId());
        assertEquals("Producto", result.getNombre());
        assertEquals(TipoProducto.COMIDA, result.getTipo());
        assertEquals(10.0, result.getPrecio());
    }

    @Test
    @DisplayName("Repository should return a list of products")
    void findAllTest() {
        Producto producto1 = new Producto(1, "Producto 1", TipoProducto.LIBROS, 55.99);
        Producto producto2 = new Producto(2, "Producto 2", TipoProducto.COMIDA, 25.08);

        manager.persist(producto1);
        manager.persist(producto2);

        List<Producto> result = repository.findAll();

        assertEquals(2, result.size());
        assertEquals(producto1.getId(), result.get(0).getId());
        assertEquals(producto2.getId(), result.get(1).getId());
    }

    @Test
    @DisplayName("Repository should filter products by type")
    void findByTypeTest() {
        Producto producto1 = new Producto(1, "Producto 1", TipoProducto.LIBROS, 55.99);
        Producto producto2 = new Producto(2, "Producto 2", TipoProducto.COMIDA, 25.08);
        Producto producto3 = new Producto(3, "Producto 3", TipoProducto.LIBROS, 89.35);

        manager.persist(producto1);
        manager.persist(producto2);
        manager.persist(producto3);

        List<Producto> result = repository.findByTipo(TipoProducto.LIBROS);
        List<Producto> result3 = repository.findByTipo(TipoProducto.ELECTRONICOS);

        assertEquals(2, result.size());
        assertEquals(producto1.getId(),result.get(0).getId());
        assertEquals(producto3.getId(),result.get(1).getId());

        assertTrue(result3.isEmpty());
    }

    @Test
    @DisplayName("Repository should find a product")
    void findByIdTest() {
        Producto producto = new Producto(1, "Producto", TipoProducto.COMIDA, 10.0);

        manager.persist(producto);

        Producto result = repository.findById(producto.getId()).orElse(null);

        assertNotNull(result);
        assertEquals(producto.getId(), result.getId());
        assertEquals("Producto", result.getNombre());
        assertEquals(TipoProducto.COMIDA, result.getTipo());
        assertEquals(10.0, result.getPrecio());
    }

    @Test
    @DisplayName("Repository should delete a product")
    void deleteByIdTest() {
        Producto producto = new Producto(1, "Producto 1", TipoProducto.LIBROS, 55.99);

        manager.persist(producto);

        repository.deleteById(producto.getId());

        List<Producto> result = repository.findAll();

        assertTrue(result.isEmpty());
    }
}