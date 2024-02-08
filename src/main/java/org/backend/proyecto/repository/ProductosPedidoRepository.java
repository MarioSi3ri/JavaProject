package org.backend.proyecto.repository;

import org.backend.proyecto.model.ProductosPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductosPedidoRepository extends JpaRepository<ProductosPedido, Long> {

    boolean existsByProductoId(long id);

    void deleteByProductoId(long id);

    @Query("SELECT SUM(pp.subTotal) FROM ProductosPedido pp WHERE pp.pedido.id = :id")
    Double findSumSubTotalByPedidoId(long id);

    Optional<List<ProductosPedido>> findByPedidoId(long id);
}
