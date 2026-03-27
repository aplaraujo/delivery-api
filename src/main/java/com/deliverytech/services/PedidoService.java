package com.deliverytech.services;

import com.deliverytech.entities.Pedido;
import com.deliverytech.entities.StatusPedido;

import java.util.List;
import java.util.Optional;

public interface PedidoService {
    Pedido criar(Pedido pedido);
    Optional<Pedido> buscarPorId(Long id);
    List<Pedido> buscarPorClientes(Long clienteId);
    List<Pedido> buscarPorRestaurantes(Long restauranteId);
    Pedido atualizarStatus(Long id, StatusPedido status);
    void cancelar(Long id);
}
