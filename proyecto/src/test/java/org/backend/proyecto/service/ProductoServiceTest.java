package org.backend.proyecto.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.backend.proyecto.dto.ProductoDTO;
import org.backend.proyecto.model.Producto;
import org.backend.proyecto.model.TipoProducto;
import org.backend.proyecto.repository.ProductoRepository;
import org.backend.proyecto.service.ProductoService;
import java.util.List;
import java.util.Arrays;

class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllProductos() {
        // Obtención de todos los productos.
        when(productoRepository.findAll()).thenReturn(Arrays.asList(
                new Producto(1, "Producto1", TipoProducto.COMIDA, 10.0),
                new Producto(2, "Producto2", TipoProducto.ELECTRONICOS, 20.0)
        ));

        List<ProductoDTO> productosDTO = productoService.findAllProductos();

        assertEquals(2, productosDTO.size());
        assertEquals(1, productosDTO.get(0).getId());
        assertEquals(2, productosDTO.get(1).getId());
    }

}