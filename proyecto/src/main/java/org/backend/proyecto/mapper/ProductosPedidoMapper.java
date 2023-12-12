package org.backend.proyecto.mapper;

import org.backend.proyecto.dto.PedidoFinalDTO;
import org.backend.proyecto.dto.ProductoFinalDTO;
import org.backend.proyecto.dto.ProductosPedidoDTO;
import org.backend.proyecto.model.ProductosPedido;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProductosPedidoMapper {

    //
    @Mapping(source = "pedidoId", target = "pedido.id")
    @Mapping(source = "productoId", target = "producto.id")
    @Mapping(target = "id", ignore = true)
    ProductosPedido toModel(Long pedidoId, Long productoId, Integer cantidad, Double subTotal);


    @Mapping(source = "productosPedido.pedido.id", target = "pedidoId")
    @Mapping(source = "productosPedido.producto.id", target = "productoId")
    ProductosPedidoDTO toDto(ProductosPedido productosPedido);

    PedidoFinalDTO toPedidoFinalDto(long id, double total, List<ProductoFinalDTO> productos);

    @Mapping(source = "productosPedido.producto.nombre", target = "nombre")
    @Mapping(source = "productosPedido.producto.precio", target = "precio")
    @Mapping(source = "productosPedido.cantidad", target = "cantidad")
    ProductoFinalDTO toProductoFinalDto(ProductosPedido productosPedido);

    List<ProductoFinalDTO> toProductoFinalDto(List<ProductosPedido> productosPedidos);
}
