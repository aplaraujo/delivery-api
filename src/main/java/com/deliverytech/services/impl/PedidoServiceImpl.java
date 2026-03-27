package com.deliverytech.services.impl;

import com.deliverytech.entities.Pedido;
import com.deliverytech.entities.StatusPedido;
import com.deliverytech.repositories.PedidoRepository;
import com.deliverytech.services.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
    private final PedidoRepository pedidoRepository;

    @Override
    public Pedido criar(Pedido pedido) {
        pedido.setStatus(StatusPedido.CRIADO);
        return pedidoRepository.save(pedido);
    }

    @Override
    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    @Override
    public List<Pedido> buscarPorClientes(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    @Override
    public List<Pedido> buscarPorRestaurantes(Long restauranteId) {
        return pedidoRepository.findByRestauranteId(restauranteId);
    }

    @Override
    public Pedido atualizarStatus(Long id, StatusPedido status) {
        return pedidoRepository.findById(id)
                .map(pedido -> {
                    pedido.setStatus(status);
                    return pedidoRepository.save(pedido);
                }).orElseThrow(() -> new RuntimeException("PedidoResponse não encontrado"));
    }

    @Override
    public void cancelar(Long id) {
        pedidoRepository.findById(id).ifPresent(pedido -> {
            pedido.setStatus(StatusPedido.CANCELADO);
            pedidoRepository.save(pedido);
        });
    }
}
