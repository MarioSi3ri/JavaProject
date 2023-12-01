package org.backend.proyecto.service;

import java.util.List;
import java.util.stream.Collectors;
import org.backend.proyecto.dto.CreateProductoDTO;
import org.backend.proyecto.dto.ProductoDTO;
import org.backend.proyecto.dto.UpdateProductoDTO;
import org.backend.proyecto.mapper.ProductoMapper;
import org.backend.proyecto.model.Producto;
import org.backend.proyecto.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repository;

    @Autowired
    private ProductoMapper mapper;

    // Obtiene todos los productos y los convierte a DTOs.
    public List<ProductoDTO> findAllProductos() {
        List<Producto> productos = repository.findAll();
        return productos.stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    // Guarda un nuevo producto en la base de datos.
    public ProductoDTO saveProducto(CreateProductoDTO data) {
        Producto entity = repository.save(mapper.toModel(data));
        return mapper.toDTO(entity);
    }

    // Actualiza un producto existente en la base de datos.
    public ProductoDTO updateProducto(Long id, UpdateProductoDTO data) {
        Producto entity = repository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("Producto no encontrado con el id: " + id));
        entity = mapper.updateModelFromDTO(data, entity);
        repository.save(entity);
        return mapper.toDTO(entity);
    }

    // Elimina un producto de la base de datos por su ID.
    public void deleteProducto(Long id) {
        repository.deleteById(id);
    }
}

/*
Utiliza el repositorio para acceder a los datos y el mapeador para convertir entre modelos y DTOs. 
*/