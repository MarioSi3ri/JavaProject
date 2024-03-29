package org.backend.proyecto.service;

import org.backend.proyecto.dto.CreateProductoDTO;
import org.backend.proyecto.dto.ProductoDTO;
import org.backend.proyecto.dto.UpdateProductoDTO;
import org.backend.proyecto.exception.ProductoNotFoundException;
import org.backend.proyecto.mapper.ProductoMapper;
import org.backend.proyecto.model.Producto;
import org.backend.proyecto.model.TipoProducto;
import org.backend.proyecto.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repository;

    @Autowired
    private ProductoMapper mapper;

    // Obtiene todos los productos y los convierte a DTOs.
    public List<ProductoDTO> findAllProductos() {
        List<Producto> productos = repository.findAll();
        return productos.stream().map(mapper::toDTO).toList();
    }

    public ProductoDTO getProducto(long id) throws ProductoNotFoundException {
        return mapper.toDTO(findProduct(id));
    }

    // Guarda un nuevo producto en la base de datos.
    public ProductoDTO saveProducto(CreateProductoDTO data) {
        Producto entity = repository.save(mapper.toModel(data));
        return mapper.toDTO(entity);
    }

    // Actualiza un producto existente en la base de datos.
    @SuppressWarnings("null")
    public ProductoDTO updateProducto(Long id, UpdateProductoDTO data) throws ProductoNotFoundException {
        Producto entity = repository.findById(id).orElseThrow(() -> new ProductoNotFoundException(id));

        return mapper.toDTO(repository.save(mapper.updateModelFromDTO(entity, data)));
    }

    // Elimina un producto de la base de datos por su ID.
    @SuppressWarnings("null")
    public void deleteProducto(Long id) {
        repository.deleteById(id);
    }

    public Producto findProduct(long id) throws ProductoNotFoundException {
        return repository.findById(id).orElseThrow(() -> new ProductoNotFoundException(id));
    }

    // Encuentra productos por el tipo de producto
    public List<ProductoDTO> findProductosTipo(TipoProducto tipo) {
        List<Producto> productos = repository.findByTipo(tipo);

        return productos.stream().map(mapper::toDTO).toList();
    }
}

/*
Utiliza el repositorio para acceder a los datos y el mapeador para convertir entre modelos y DTOs. 
*/