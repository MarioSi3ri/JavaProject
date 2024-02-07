package org.backend.proyecto.service;

import org.backend.proyecto.dto.CreateProductoDTO;
import org.backend.proyecto.dto.UpdateProductoDTO;
import org.backend.proyecto.exception.ProductoNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.backend.proyecto.dto.ProductoDTO;
import org.backend.proyecto.model.Producto;
import org.backend.proyecto.model.TipoProducto;
import org.backend.proyecto.repository.ProductoRepository;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ProductoServiceTest {

    @MockBean
    private ProductoRepository repository;

    @Autowired
    private ProductoService service;

    @Test
    @DisplayName("Service should be injected")
    void smokeTest() {
        assertNotNull(service);
    }

    @Test
    @DisplayName("Service should return products form repository")
    void findAllProductsTest() {
        Producto producto1 = new Producto(1, "Producto1", TipoProducto.COMIDA, 10.0);
        Producto producto2 = new Producto(2, "Producto2", TipoProducto.ELECTRONICOS, 20.0);

        when(repository.findAll()).thenReturn(Arrays.asList(producto1, producto2));

        List<ProductoDTO> result = service.findAllProductos();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(producto1.getId(), result.get(0).getId());
        assertEquals(producto2.getId(), result.get(1).getId());
    }

    @Test
    @DisplayName("Service should return products of a type from repository")
    void findProductsTypeTest() {
        Producto producto1 = new Producto(1, "Producto 1", TipoProducto.LIBROS, 55.99);
        Producto producto2 = new Producto(3, "Producto 3", TipoProducto.LIBROS, 89.35);

        when(repository.findByTipo(any(TipoProducto.class))).thenReturn(Arrays.asList(producto1, producto2));

        List<ProductoDTO> result = service.findProductosTipo(TipoProducto.LIBROS);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(producto1.getId(), result.get(0).getId());
        assertEquals(producto2.getId(), result.get(1).getId());
    }

    @Test
    @DisplayName("Service should return a specific product from repository")
    void getProductTest() throws ProductoNotFoundException {
        Producto producto = new Producto(1, "Producto", TipoProducto.LIBROS, 55.99);

        when(repository.findById(anyLong())).thenReturn(Optional.of(producto));

        ProductoDTO result = service.getProducto(producto.getId());

        assertNotNull(result);
        assertEquals(producto.getId(), result.getId());
        assertEquals("Producto", result.getNombre());
        assertEquals(TipoProducto.LIBROS, result.getTipo());
        assertEquals(55.99, result.getPrecio());
    }

    @Test
    @DisplayName("Service should save a product in repository")
    void saveProductTest() {
        CreateProductoDTO dto = new CreateProductoDTO();

        dto.setCodigo(1);
        dto.setNombre("Producto");
        dto.setTipo(TipoProducto.ELECTRONICOS);
        dto.setPrecio(115.26);

        Producto model = new Producto(dto.getCodigo(), dto.getNombre(), dto.getTipo(), dto.getPrecio());

        when(repository.save(any(Producto.class))).thenReturn(model);

        ProductoDTO result = service.saveProducto(dto);

        assertNotNull(result);
        assertEquals(model.getId(), result.getId());
        assertEquals(model.getNombre(), result.getNombre());
        assertEquals(model.getTipo(), result.getTipo());
        assertEquals(model.getPrecio(), result.getPrecio());
    }

    @Test
    @DisplayName("Service should update a product in repository")
    void updateProductTest() throws ProductoNotFoundException {
        UpdateProductoDTO dto = new UpdateProductoDTO();

        dto.setNombre("Producto actualizado");
        dto.setTipo(TipoProducto.ROPA);
        dto.setPrecio(546.99);

        Producto producto = new Producto(55, "Producto", TipoProducto.JUGUETES, 100.01);

        when(repository.findById(anyLong())).thenReturn(Optional.of(producto));

        service.updateProducto(producto.getId(), dto);

        assertEquals(dto.getNombre(), producto.getNombre());
        assertEquals(dto.getTipo(), producto.getTipo());
        assertEquals(dto.getPrecio(), producto.getPrecio());
    }

    @Test
    @DisplayName("Service should delete a product by id from repository")
    void deleteProductTest() {
        service.deleteProducto(4555L);

        verify(repository, times(1)).deleteById(4555L);
    }

    @Test
    @DisplayName("Service should throw an error if product was not found")
    void findProductWithErrorTest() {
        Optional<Producto> dummy = Optional.empty();

        when(repository.findById(anyLong())).thenReturn(dummy);

        assertThrows(ProductoNotFoundException.class, () -> service.getProducto(10));
    }
}