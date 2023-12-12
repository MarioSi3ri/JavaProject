package org.backend.proyecto.mapper;

import org.backend.proyecto.dto.CreateProductoDTO;
import org.backend.proyecto.dto.ProductoDTO;
import org.backend.proyecto.dto.UpdateProductoDTO;
import org.backend.proyecto.model.Producto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductoMapper {

    // Convierte un modelo 'Producto' a un ProductoDTO'.
    ProductoDTO toDTO(Producto model);

    // Convierte un 'CreateProductoDTO' a un modelo 'Producto'.
    @Mapping(target = "id", ignore = true)
    Producto toModel(CreateProductoDTO dto);

    // Actualiza un modelo 'Producto' desde un 'UpdateProductoDTO'.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "codigo", ignore = true)
    Producto updateModelFromDTO(@MappingTarget Producto model, UpdateProductoDTO dto);
}

/*
Este controlador de MapStruct es encargado de mapear entre modelos (Producto) y DTOs (ProductoDTO, CreateProductoDTO y UpdateProductoDTO).
*/