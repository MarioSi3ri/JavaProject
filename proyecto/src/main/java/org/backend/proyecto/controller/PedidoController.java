package org.backend.proyecto.controller;

import org.backend.proyecto.dto.PedidoDTO;
import org.backend.proyecto.exception.PedidoNotFoundException;
import org.backend.proyecto.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // Mapea la solicitud POST para crear un nuevo pedido
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO createPedido() {
        return pedidoService.createPedido();
    }

    // Mapea la solicitud GET a la lista de pedidos
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PedidoDTO> getPedidos() {
        return pedidoService.getAll();
    }

    // Mapea la solicitud GET de un pedido de id específico
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PedidoDTO getPedido(@PathVariable long id) throws PedidoNotFoundException {
        return pedidoService.getPedido(id);
    }

}
