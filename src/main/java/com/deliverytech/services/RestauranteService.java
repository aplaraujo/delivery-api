package com.deliverytech.services;

import com.deliverytech.entities.Restaurante;

import java.util.List;
import java.util.Optional;

public interface RestauranteService {
    Restaurante cadastrar(Restaurante restaurante);
    Optional<Restaurante> buscarPorId(Long id);
    List<Restaurante> buscarTodos();
    List<Restaurante> buscarPorCategoria(String categoria);
    Restaurante atualizar(Long id, Restaurante restauranteAtualizado);
}
