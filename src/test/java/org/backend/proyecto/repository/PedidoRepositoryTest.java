package org.backend.proyecto.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;
import org.backend.proyecto.model.Pedido;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@DataJpaTest
class PedidoRepositoryTest {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private TestEntityManager manager;

    @Test
    @DisplayName("Repository should be injected")
    void smokeTest() {
        assertNotNull(repository);
    }

    @Test
    @DisplayName("Repository should save an order")
    void savePedidoTest() {
        Pedido pedido = new Pedido();

        repository.save(pedido);

        Pedido result = repository.findAll().get(0);

        assertNotNull(result);
        assertEquals(pedido.getId(), result.getId());
        assertTrue(result.isActivo());
        assertEquals(0.0, result.getTotal());
    }

    @Test
    @DisplayName("Repository should return a list of orders")
    void findAllTest() {
        Pedido pedido1 = new Pedido();
        Pedido pedido2 = new Pedido();
        Pedido pedido3 = new Pedido();

        manager.persist(pedido1);
        manager.persist(pedido2);
        manager.persist(pedido3);

        List<Pedido> result = repository.findAll();

        assertEquals(3, result.size());
        assertEquals(pedido1.getId(), result.get(0).getId());
        assertEquals(pedido2.getId(), result.get(1).getId());
        assertEquals(pedido3.getId(), result.get(2).getId());
    }

    @Test
    @DisplayName("Repository should return the active order")
    void findByActiveTrueTest() {
        Pedido pedido1 = new Pedido();
        Pedido pedido2 = new Pedido();

        pedido2.setActivo(false);

        manager.persist(pedido1);
        manager.persist(pedido2);

        Optional<Pedido> result = repository.findByActivoTrue();

        assertNotNull(result);
        assertEquals(pedido1.getId(), result.get().getId());
    }

    @Test
    @DisplayName("Repository should find a product")
    void findByIdTest() {
        Pedido pedido = new Pedido();

        manager.persist(pedido);

        Pedido result = repository.findById(pedido.getId()).orElse(null);

        assertNotNull(result);
        assertEquals(pedido.getId(), result.getId());
    }
}