package com.deliverytech.services.impl;

import com.deliverytech.entities.Restaurante;
import com.deliverytech.repositories.RestauranteRepository;
import com.deliverytech.services.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestauranteServiceImpl implements RestauranteService {
    private final RestauranteRepository restauranteRepository;

    @Override
    public Restaurante cadastrar(Restaurante restaurante) {
        return restauranteRepository.save(restaurante);
    }

    @Override
    public Optional<Restaurante> buscarPorId(Long id) {
        return restauranteRepository.findById(id);
    }

    @Override
    public List<Restaurante> buscarTodos() {
        return restauranteRepository.findAll();
    }

    @Override
    public List<Restaurante> buscarPorCategoria(String categoria) {
        return restauranteRepository.findByCategoria(categoria);
    }

    @Override
    public Restaurante atualizar(Long id, Restaurante restauranteAtualizado) {
        return restauranteRepository.findById(id)
                .map(restaurante -> {
                    restaurante.setNome(restauranteAtualizado.getNome());
                    restaurante.setTelefone(restauranteAtualizado.getTelefone());
                    restaurante.setCategoria(restauranteAtualizado.getCategoria());
                    restaurante.setTaxaEntrega(restauranteAtualizado.getTaxaEntrega());
                    restaurante.setTempoEntregaMinutos(restauranteAtualizado.getTempoEntregaMinutos());
                    return  restauranteRepository.save(restaurante);
                }).orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));
    }
}
