package org.backend.proyecto.controller;

import java.util.List;
import org.backend.proyecto.dto.CreateProductoDTO;
import org.backend.proyecto.dto.ProductoDTO;
import org.backend.proyecto.dto.UpdateProductoDTO;
import org.backend.proyecto.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("productos")
public class ProductoController {

    @Autowired
    private ProductoService service;

    // Mapea la solicitud GET a la lista de productos.
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductoDTO> getAllProductos() {
        return service.findAllProductos();
    }

    // Mapea la solicitud POST para crear un nuevo producto.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductoDTO createProducto(@Valid @RequestBody CreateProductoDTO data) {
        return service.saveProducto(data);
    }

    // Mapea la solicitud PUT para actualizar un producto existente.
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductoDTO updateProducto(@PathVariable Long id, @Valid @RequestBody UpdateProductoDTO data) {
        return service.updateProducto(id, data);
    }

    // Mapea la solicitud DELETE para eliminar un producto por su ID.
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProducto(@PathVariable Long id) {
        service.deleteProducto(id);
    }
}

/*
Es el controlador para gestionar las operaciones CRUD de los productos.
Se aplican los métodos para obtener todos los productos, crear uno nuevo, actualizar y eliminar un producto.
*/