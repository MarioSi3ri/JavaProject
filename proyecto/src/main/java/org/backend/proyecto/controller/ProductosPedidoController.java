package org.backend.proyecto.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.backend.proyecto.dto.AddProductoDTO;
import org.backend.proyecto.dto.PedidoFinalDTO;
import org.backend.proyecto.dto.ProductoFinalDTO;
import org.backend.proyecto.dto.ProductosPedidoDTO;
import org.backend.proyecto.exception.PedidoNotFoundException;
import org.backend.proyecto.exception.ProductoNotFoundException;
import org.backend.proyecto.service.ProductosPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class ProductosPedidoController {

    @Autowired
    private ProductosPedidoService productosPedidoService;

    // Mapea la solicitud POST para añadir un producto a un pedido
    @PostMapping("/productos")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductosPedidoDTO addProducto(@RequestBody @Valid AddProductoDTO dto) throws PedidoNotFoundException, ProductoNotFoundException {
        return productosPedidoService.addProducto(dto);
    }

    // Mapea la solicitud DELETE para quitar un producto de un pedido
    @DeleteMapping("productos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeProducto(@PathVariable long id) throws ProductoNotFoundException {
        productosPedidoService.removeProducto(id);
    }

    // Mapea la solicitud PUT para desactivar y finalizar un pedido, arrojando el "ticket" de compra
    @PutMapping("/finalizar")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public PedidoFinalDTO finalizarPedido() throws PedidoNotFoundException {
        return productosPedidoService.closePedido();
    }

    // Mapea la solicitud GET para la lista de productos que tiene un pedido
    @GetMapping("/{id}/productos")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductoFinalDTO> getProductosPedido(@PathVariable long id) throws PedidoNotFoundException {
        return productosPedidoService.getProductosPorPedido(id);
    }
}








