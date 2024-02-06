package org.backend.proyecto.repository;

import org.backend.proyecto.model.Producto;
import org.backend.proyecto.model.TipoProducto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends CrudRepository<Producto, Long> {

    // Método para obtener todos los productos.
    @SuppressWarnings("null")
    List<Producto> findAll();

    List<Producto> findByTipo(TipoProducto tipo);
}

/*
Es un repositorio de 'Spring Data JPA' que extiende 'CrudRepository'
y se utiliza para acceder a los datos de productos en la base de datos. 
*/