package org.backend.proyecto.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.backend.proyecto.dto.ErrorDTO;
import org.backend.proyecto.dto.PedidoDTO;
import org.backend.proyecto.model.Pedido;
import org.backend.proyecto.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PedidoControllerE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PedidoRepository repository;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("GET /pedidos should return an empty list")
    void emptyListTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/pedidos"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertEquals("[]", content);
    }

    @Test
    @DisplayName("GET /pedidos should return a list of orders")
    void getAllTest() throws Exception {
        Pedido pedido1 = new Pedido();
        Pedido pedido2 = new Pedido();

        repository.save(pedido1);
        repository.save(pedido2);

        MvcResult result = mockMvc.perform(get("/pedidos"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        List<PedidoDTO> response = mapper.readValue(content, new TypeReference<>() {});

        assertEquals(2, response.size());
        assertEquals(pedido1.getId(), response.get(0).getId());
        assertEquals(pedido2.getId(), response.get(1).getId());
    }

    @Test
    @DisplayName("GET /pedidos/id should return a specific order")
    void getOrderTest() throws Exception {
        Pedido pedido = new Pedido();
        repository.save(pedido);

        MvcResult result = mockMvc.perform(get("/pedidos/" + pedido.getId()))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        PedidoDTO response = mapper.readValue(content, new TypeReference<>() {});

        assertEquals(pedido.getId(), response.getId());
    }
    @Test
    @DisplayName("GET /pedidos/id should return an error if the order does not exists")
    void orderNotFoundTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/pedidos/1"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        ErrorDTO response = mapper.readValue(content, ErrorDTO.class);

        assertEquals("ERR_PED_NOT_FOUND", response.getCode());
        assertEquals("El pedido no existe", response.getMessage());
        assertEquals(1, response.getDetails());

    }

    @Test
    @DisplayName("POST /pedidos should save an order")
    void createOrderTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/pedidos"))
                .andExpect(status().isCreated())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        PedidoDTO response = mapper.readValue(content, new TypeReference<>(){});

        assertTrue(response.getId() >= 0);
        assertTrue(response.isActivo());
        assertEquals(0.0, response.getTotal());
    }
}
