package org.backend.proyecto.repository;

import java.util.List;
import org.backend.proyecto.model.Producto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends CrudRepository<Producto, Long> {

    // Método para obtener todos los productos.
    List<Producto> findAll();
}

/*
Es un repositorio de 'Spring Data JPA' que extiende 'CrudRepository'
y se utiliza para acceder a los datos de productos en la base de datos. 
*/