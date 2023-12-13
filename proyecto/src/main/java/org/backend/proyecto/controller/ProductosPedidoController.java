package org.backend.proyecto.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Endpoints de Producto-Pedido", description = "CRUD para las acciones que unen los productos y pedidos")
@RestController
@RequestMapping("/pedidos")
public class ProductosPedidoController {

    @Autowired
    private ProductosPedidoService productosPedidoService;

    // Mapea la solicitud POST para añadir un producto a un pedido
    @Operation(summary = "Se encarga de mapear la solicitud POST para añadir un producto a un pedido")
    @PostMapping("/productos")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductosPedidoDTO addProducto(@RequestBody @Valid AddProductoDTO dto) throws PedidoNotFoundException, ProductoNotFoundException {
        return productosPedidoService.addProducto(dto);
    }

    // Mapea la solicitud DELETE para quitar un producto de un pedido
    @Operation(summary = "Se encarga de mapear la solicitud DELETE para quitar un producto de un pedido")
    @DeleteMapping("productos/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeProducto(@PathVariable long id) throws ProductoNotFoundException {
        productosPedidoService.removeProducto(id);
    }

    // Mapea la solicitud PUT para desactivar y finalizar un pedido, arrojando el "ticket" de compra
    @Operation(summary = "Se encarga de mapear la solicitud PUT para desactivar y finalizar un pedido, arrojando el \"ticket\" de compra")
    @PutMapping("/finalizar")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public PedidoFinalDTO finalizarPedido() throws PedidoNotFoundException {
        return productosPedidoService.closePedido();
    }

    // Mapea la solicitud GET para la lista de productos que tiene un pedido
    @Operation(summary = "Encargado de obtener toda la lista de el pedido y que productos contiene")
    @GetMapping("/{id}/productos")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductoFinalDTO> getProductosPedido(@PathVariable long id) throws PedidoNotFoundException {
        return productosPedidoService.getProductosPorPedido(id);
    }
}








