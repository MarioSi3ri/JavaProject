package org.backend.proyecto.service;

import org.backend.proyecto.dto.AddProductoDTO;
import org.backend.proyecto.dto.PedidoFinalDTO;
import org.backend.proyecto.dto.ProductoFinalDTO;
import org.backend.proyecto.dto.ProductosPedidoDTO;
import org.backend.proyecto.exception.PedidoNotFoundException;
import org.backend.proyecto.exception.ProductoNotFoundException;
import org.backend.proyecto.mapper.ProductosPedidoMapper;
import org.backend.proyecto.model.Pedido;
import org.backend.proyecto.model.Producto;
import org.backend.proyecto.model.ProductosPedido;
import org.backend.proyecto.repository.ProductosPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductosPedidoService {

    @Autowired
    private ProductosPedidoRepository repository;

    @Autowired
    private ProductosPedidoMapper productosPedidoMapper;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private PedidoService pedidoService;

    // Agrega un producto al pedido que se encuentre activo
    public ProductosPedidoDTO addProducto(AddProductoDTO dto) throws PedidoNotFoundException, ProductoNotFoundException {
        Pedido pedido = pedidoService.findActivo();
        Producto producto = productoService.findProduct(dto.getId());

        double subTotal = producto.getPrecio() * dto.getCantidad();

        //Guarda la relación de producto y pedido en la db, tabla productos_por_pedido
        ProductosPedido productosPedido = repository
                .save(productosPedidoMapper.toModel(
                        pedido.getId(),
                        producto.getId(),
                        dto.getCantidad(),
                        subTotal));

        return productosPedidoMapper.toDto(productosPedido);
    }

    // Elimina un producto del pedido activo
    public void removeProducto(long id) throws ProductoNotFoundException {
        if (!repository.existsByProductoId(id)) throw new ProductoNotFoundException(id);

        repository.deleteByProductoId(id);
    }

    // Desactiva el pedido actual, calcula el total y arroja un dto tipo ticket que muestra:
    // idPedido, total y una lista de los productos del pedido: nombre, cantida y precio
    public PedidoFinalDTO closePedido() throws PedidoNotFoundException {
        Pedido pedido = pedidoService.findActivo();
        double total = repository.findSumSubTotalByPedidoId(pedido.getId());

        pedido.finalizar(total);
        return productosPedidoMapper.toPedidoFinalDto(pedido.getId(), total, getProductosPorPedido(pedido.getId()));
    }

    // Arroja una lista de los productos que forman parte de un pedido, que puede o no estar activo
    public List<ProductoFinalDTO> getProductosPorPedido(long id) throws PedidoNotFoundException {
        List<ProductosPedido> productosPedidos = repository.findByPedidoId(id)
                .orElseThrow(() -> new PedidoNotFoundException(id));
        return productosPedidoMapper.toProductoFinalDto(productosPedidos);
    }
}













