package org.backend.proyecto.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "producto")
public class Producto {

    // Identificador (ID) del producto.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // Código del producto (único).
    @Column(unique = true)
    @Min(1)
    private int codigo;

    // Nombre del producto.
    @Column(nullable = false, length = 100)
    private String nombre;

    // Tipo de producto (enum).
    @Enumerated
    @Column(nullable = false)
    private TipoProducto tipo;

    @Column(nullable = false)
    private double precio;
}

/*
Modelo de datos para un producto, que se almacena en la base de datos. Se define un ID único, código, nombre y tipo de producto.
*/