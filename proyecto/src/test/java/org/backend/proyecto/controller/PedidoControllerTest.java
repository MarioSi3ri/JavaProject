package org.backend.proyecto.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.when;
import org.backend.proyecto.controller.PedidoController;
import org.backend.proyecto.dto.PedidoDTO;
import org.backend.proyecto.service.PedidoService;

class PedidoControllerTest {

    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private PedidoController pedidoController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pedidoController).build();
    }

    @SuppressWarnings("null")
    @Test
    void testCreatePedido() throws Exception {
        // Configuración del mock.
        PedidoDTO pedidoDTO = new PedidoDTO();
        when(pedidoService.createPedido()).thenReturn(pedidoDTO);

        // Solicitud HTTP POST al endpoint '/pedidos'.
        mockMvc.perform(post("/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.activo").value(true))
                .andExpect(jsonPath("$.total").value(0.0));
    }
}