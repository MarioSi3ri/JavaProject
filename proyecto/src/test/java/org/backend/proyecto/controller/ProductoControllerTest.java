package org.backend.proyecto.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import org.backend.proyecto.controller.ProductoController;
import org.backend.proyecto.dto.ProductoDTO;
import org.backend.proyecto.service.ProductoService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ProductoControllerTest {

    @Mock
    private ProductoService productoService;

    @InjectMocks
    private ProductoController productoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("null")
    @Test
    void testGetAllProductos() {
        // Mock de los productos.
        List<ProductoDTO> productosMock = Arrays.asList(
                new ProductoDTO()
        );

        when(productoService.findAllProductos()).thenReturn(productosMock);

        // Llamada al método del controlador.
        @SuppressWarnings("unchecked")
        ResponseEntity<List<ProductoDTO>> response = (ResponseEntity<List<ProductoDTO>>) productoController.getAllProductos();

        // Verificación de la respuesta.
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }
}