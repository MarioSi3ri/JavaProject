package org.backend.proyecto.service;

import org.backend.proyecto.dto.PedidoDTO;
import org.backend.proyecto.exception.PedidoNotFoundException;
import org.backend.proyecto.mapper.PedidoMapper;
import org.backend.proyecto.model.Pedido;
import org.backend.proyecto.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    // Crea un nuevo pedido y lo activa
    public PedidoDTO createPedido() {
        return pedidoMapper.toDto(pedidoRepository.save(new Pedido()));
    }

    // Busca en la db si hay algún pedido activo
    public Pedido findActivo() throws PedidoNotFoundException {
        return pedidoRepository.findByActivoTrue().orElseThrow(() -> new PedidoNotFoundException(null));
    }

    // Arroja una lista de todos los pedidos que hay
    public List<PedidoDTO> getAll() {
        return pedidoRepository.findAll().stream().map(pedido -> pedidoMapper.toDto(pedido)).toList();
    }

    // Obtiene un pedido de id específico
    public PedidoDTO getPedido(long id) throws PedidoNotFoundException {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new PedidoNotFoundException(id));

        return pedidoMapper.toDto(pedido);
    }
}
