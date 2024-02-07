package org.backend.proyecto.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.backend.proyecto.dto.ErrorDTO;
import org.backend.proyecto.dto.ProductoDTO;
import org.backend.proyecto.model.Producto;
import org.backend.proyecto.model.TipoProducto;
import org.backend.proyecto.repository.ProductoRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProductoControllerE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductoRepository repository;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("GET /productos should return an empty list")
    void emptyListTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/productos"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertEquals("[]", content);
    }

    @Test
    @DisplayName("GET /productos should return a list of products")
    void getAllTest() throws Exception {
        Producto producto1 = new Producto(1, "Producto genérico", TipoProducto.LIBROS, 55.99);
        Producto producto2 = new Producto(2, "Producto genérico 2", TipoProducto.COMIDA, 25.08);

        repository.save(producto1);
        repository.save(producto2);

        MvcResult result = mockMvc.perform(get("/productos"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        List<ProductoDTO> response = mapper.readValue(content, new TypeReference<>() {});

        assertEquals(2, response.size());
        assertEquals(producto1.getId(), response.get(0).getId());
        assertEquals(producto2.getId(), response.get(1).getId());
    }

    @Test
    @DisplayName("GET /productos/id should return a specific product")
    void getProductoTest() throws Exception {
        Producto producto = new Producto(1, "Producto genérico", TipoProducto.LIBROS, 55.99);
        repository.save(producto);

        MvcResult result = mockMvc.perform(get("/productos/" + producto.getId()))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        ProductoDTO response = mapper.readValue(content, new TypeReference<>() {});

        assertEquals(producto.getId(), response.getId());
    }
    @Test
    @DisplayName("GET /productos/id should return an error if the product does not exists")
    void productNotFoundTest() throws Exception {

        MvcResult result = mockMvc.perform(get("/productos/1"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        ErrorDTO response = mapper.readValue(content, ErrorDTO.class);

        assertEquals("ERR_PROD_NOT_FOUND", response.getCode());
        assertEquals("El producto no existe", response.getMessage());
        assertEquals(1, response.getDetails());

    }

    @Test
    @DisplayName("GET /productos?tipo=TIPO should return an empty list")
    void emptyTypeListTest() throws Exception {

        MvcResult result = mockMvc.perform(get("/productos?tipo=COMIDA"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertEquals("[]", content);
    }

    @Test
    @DisplayName("GET /productos?tipo=TIPO should return a list of products of the same type")
    void getAllByTypeTest() throws Exception {
        Producto producto1 = new Producto(1, "Producto genérico 1", TipoProducto.LIBROS, 55.99);
        Producto producto2 = new Producto(2, "Producto genérico 2", TipoProducto.LIBROS, 25.08);

        repository.save(producto1);
        repository.save(producto2);

        MvcResult result = mockMvc.perform(get("/productos?tipo=LIBROS"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        List<ProductoDTO> response = mapper.readValue(content, new TypeReference<>() {});

        assertEquals(2, response.size());
        assertEquals(TipoProducto.LIBROS, response.get(0).getTipo());
        assertEquals(TipoProducto.LIBROS, response.get(1).getTipo());
    }

    @Test
    @DisplayName("POST /productos should save a product")
    void createProductTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/productos").contentType("application/json").content("{\"codigo\":1,\"nombre\":\"Producto Generico\",\"tipo\":\"LIBROS\",\"precio\":55.06}"))
                .andExpect(status().isCreated())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        ProductoDTO response = mapper.readValue(content, new TypeReference<>(){});

        assertTrue(response.getId() >= 0);
        assertEquals(1, response.getCodigo());
        assertEquals("Producto Generico", response.getNombre());
        assertEquals(TipoProducto.LIBROS, response.getTipo());
        assertEquals(55.06, response.getPrecio());
    }

    @Test
    @DisplayName("POST /productos should return an error if code is not greater than or equal to 1")
    void invalidCodeInRequestBodyTest() throws Exception {

        MvcResult result = mockMvc.perform(post("/productos")
                        .contentType("application/json")
                        .content("{\"codigo\":0,\"nombre\":\"Producto Genérico\",\"tipo\":\"LIBROS\",\"precio\":55.06}"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        ErrorDTO response = mapper.readValue(content, ErrorDTO.class);
        List<String> details = (List<String>) response.getDetails();

        assertEquals("ERR_VALIDATION", response.getCode());
        assertEquals("Error de validación en los datos de entrada", response.getMessage());
        assertEquals("must be greater than or equal to 1", details.get(0));
    }

    @Test
    @DisplayName("POST /productos should return an error if name is missing")
    void missingNameInRequestBodyTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/productos")
                        .contentType("application/json")
                        .content("{\"codigo\":1,\"tipo\":\"LIBROS\",\"precio\":55.06}"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        ErrorDTO response = mapper.readValue(content, ErrorDTO.class);
        List<String> details = (List<String>) response.getDetails();

        assertEquals("ERR_VALIDATION", response.getCode());
        assertEquals("Error de validación en los datos de entrada", response.getMessage());
        assertEquals("must not be blank", details.get(0));
    }

    @Test
    @DisplayName("POST /productos should return an error if type is missing")
    void missingTypeInRequestBodyTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/productos")
                        .contentType("application/json")
                        .content("{\"codigo\":1,\"nombre\":\"Producto Genérico\",\"precio\":55.06}"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        ErrorDTO response = mapper.readValue(content, ErrorDTO.class);
        List<String> details = (List<String>) response.getDetails();

        assertEquals("ERR_VALIDATION", response.getCode());
        assertEquals("Error de validación en los datos de entrada", response.getMessage());
        assertEquals("must not be null", details.get(0));
    }

    @Test
    @DisplayName("POST /productos should return an error if price is not greater than or equal to 0.1")
    void missingPriceInRequestBodyTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/productos")
                        .contentType("application/json")
                        .content("{\"codigo\":1,\"nombre\":\"Producto Genérico\",\"tipo\":\"LIBROS\"}"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        ErrorDTO response = mapper.readValue(content, ErrorDTO.class);
        List<String> details = (List<String>) response.getDetails();

        assertEquals("ERR_VALIDATION", response.getCode());
        assertEquals("Error de validación en los datos de entrada", response.getMessage());
        assertEquals("must be greater than or equal to 0.1", details.get(0));
    }

    @Test
    @DisplayName("PUT /productos/id should update all possible attributes of a product")
    void updateProductTest() throws Exception {
        Producto producto = new Producto(1, "Producto genérico", TipoProducto.LIBROS, 55.99);

        repository.save(producto);

        MvcResult result = mockMvc.perform(put("/productos/" + producto.getId())
                        .contentType("application/json")
                        .content("{\"nombre\":\"Producto Actualizado\",\"tipo\":\"COMIDA\",\"precio\":26.08}"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        ProductoDTO response = mapper.readValue(content, new TypeReference<>() {});

        assertEquals(producto.getId(), response.getId());
        assertEquals("Producto Actualizado", response.getNombre());
        assertEquals(TipoProducto.COMIDA, response.getTipo());
        assertEquals(26.08, response.getPrecio());
    }

    @Test
    @DisplayName("PUT /productos/id should update the attributes of a product individually")
    void updateEachAttributeTest() throws Exception {
        Producto producto = new Producto(1, "Producto genérico", TipoProducto.LIBROS, 55.99);
        repository.save(producto);

        long id = producto.getId();

        mockMvc.perform(put("/productos/" + id).contentType("application/json").content("{\"nombre\":\"Producto Actualizado\"}"))
                .andExpect(status().isOk());
        mockMvc.perform(put("/productos/" + id).contentType("application/json").content("{\"tipo\":\"COMIDA\"}"))
                .andExpect(status().isOk());
        MvcResult result = mockMvc.perform(put("/productos/" + id).contentType("application/json").content("{\"precio\":26.08}"))
                .andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();

        ProductoDTO response = mapper.readValue(content, new TypeReference<>() {});

        assertEquals(id, response.getId());
        assertEquals("Producto Actualizado", response.getNombre());
        assertEquals(TipoProducto.COMIDA, response.getTipo());
        assertEquals(26.08, response.getPrecio());
    }

    @Test
    @DisplayName("PUT /productos/id should return an error if the product does not exists")
    void productNotFoundForUpdateTest() throws Exception {
        MvcResult result = mockMvc.perform(put("/productos/1")
                        .contentType("application/json")
                        .content("{\"nombre\":\"Producto Actualizado\",\"tipo\":\"COMIDA\",\"precio\":26.08}"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        ErrorDTO response = mapper.readValue(content, ErrorDTO.class);

        assertEquals("ERR_PROD_NOT_FOUND", response.getCode());
        assertEquals("El producto no existe", response.getMessage());
        assertEquals(1, response.getDetails());
    }

    @Test
    @DisplayName("DELETE /productos/id should delete a product")
    void deleteProductTest() throws Exception {
        Producto producto = new Producto(1, "Producto genérico", TipoProducto.LIBROS, 55.99);
        repository.save(producto);

        long id = producto.getId();

        MvcResult result = mockMvc.perform(delete("/productos/" + id))
                .andExpect(status().isNoContent())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().isEmpty());
        assertFalse(repository.existsById(id));
    }
}
