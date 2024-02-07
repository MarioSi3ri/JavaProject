package org.backend.proyecto.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private boolean activo;

    @Column(nullable = false)
    private double total;

    // Constructor sin argumentos que define valores default de total y activo
    public Pedido() {
        this.activo = true;
        this.total = 0.0;
    }

    // Método que desactiva el pedido y asigna el precio totaL
    public void finalizar(double total) {
        this.activo = false;
        this.total = total;
    }
}
