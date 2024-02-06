package org.backend.proyecto.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "productos_por_pedido")
public class ProductosPedido {

    public ProductosPedido(long l, long m, int k, double d) {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    // Cantidad del producto
    @Column(nullable = false)
    private int cantidad;

    // Sub total : cantidad * producto.precio
    @Column(name = "sub_total", nullable = false)
    private double subTotal;
}
