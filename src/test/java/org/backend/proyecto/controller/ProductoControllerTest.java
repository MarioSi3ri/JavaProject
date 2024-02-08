package org.backend.proyecto.controller;

import org.backend.proyecto.dto.CreateProductoDTO;
import org.backend.proyecto.dto.ProductoDTO;
import org.backend.proyecto.dto.UpdateProductoDTO;
import org.backend.proyecto.exception.ProductoNotFoundException;
import org.backend.proyecto.model.TipoProducto;
import org.backend.proyecto.service.ProductoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ProductoControllerTest {

    @MockBean
    private ProductoService service;

    @Autowired
    private ProductoController controller;

    @Test
    @DisplayName("Controller should be injected")
    void smokeTest() {
        assertNotNull(controller);
    }

    @Test
    @DisplayName("Controller should return a list of products")
    void getAllProductosTest() {
        ProductoDTO dto1 = new ProductoDTO(1L, 1, "Producto 1", TipoProducto.JUGUETES, 589.36);
        ProductoDTO dto2 = new ProductoDTO(2L, 2, "Producto 2", TipoProducto.JUGUETES, 584.36);

        when(service.findAllProductos()).thenReturn(Arrays.asList(dto1, dto2));

        List<ProductoDTO> result = controller.getAllProductos();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(dto1.getId(), result.get(0).getId());
        assertEquals(dto2.getId(), result.get(1).getId());
    }

    @Test
    @DisplayName("Controller should return a list of products")
    void getProductoTest() throws ProductoNotFoundException {
        ProductoDTO dto = new ProductoDTO(1L, 1, "Producto 1", TipoProducto.JUGUETES, 589.36);

        when(service.getProducto(anyLong())).thenReturn(dto);
    }

    @Test
    @DisplayName("Controller should throw an error if producto was not found")
    void getProductoWithErrorTest() throws ProductoNotFoundException {
        when(service.getProducto(anyLong())).thenThrow(ProductoNotFoundException.class);

        assertThrows(ProductoNotFoundException.class, () -> controller.getProducto(1L));
    }

    @Test
    @DisplayName("Controller should filter products by tipo")
    void getProductosTipoTest() {
        ProductoDTO dto1 = new ProductoDTO(1L, 1, "Producto 1", TipoProducto.JUGUETES, 589.36);
        ProductoDTO dto2 = new ProductoDTO(2L, 2, "Producto 2", TipoProducto.JUGUETES, 584.36);

        when(service.findProductosTipo(any(TipoProducto.class))).thenReturn(Arrays.asList(dto1, dto2));

        List<ProductoDTO> result = controller.getProductosTipo(TipoProducto.JUGUETES);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(dto1.getId(), result.get(0).getId());
        assertEquals(dto2.getId(), result.get(1).getId());
    }

    @Test
    @DisplayName("Controller should save a product")
    void createProductoTest() throws Exception {
        CreateProductoDTO data = new CreateProductoDTO(1, "Producto", TipoProducto.ROPA, 55.02);

        ProductoDTO dto = new ProductoDTO(1L, 1, "Producto", TipoProducto.ROPA, 55.02);

        when(service.saveProducto(any(CreateProductoDTO.class))).thenReturn(dto);

        ProductoDTO result = controller.createProducto(data);

        assertNotNull(result);
        assertEquals(dto.getId(), result.getId());
    }

    @Test
    @DisplayName("Controller should update a product")
    void updateProductoTest() throws ProductoNotFoundException {
        UpdateProductoDTO dto = new UpdateProductoDTO();
        dto.setNombre("Prod 1");
        dto.setTipo(TipoProducto.LIBROS);
        dto.setPrecio(44.022);

        controller.updateProducto(80L, dto);

        verify(service, times(1)).updateProducto(80L, dto);
    }

    @Test
    @DisplayName("Controller should delete a product")
    void deleteProductoTest() {
        controller.deleteProducto(8793L);

        verify(service, times(1)).deleteProducto(8793L);
    }
}