package org.backend.proyecto.mapper;

import org.backend.proyecto.dto.PedidoDTO;
import org.backend.proyecto.model.Pedido;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PedidoMapper {

    PedidoDTO toDto(Pedido pedido);
}
