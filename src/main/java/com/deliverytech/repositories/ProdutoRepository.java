package com.deliverytech.repositories;

import com.deliverytech.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByRestauranteId(Long restauranteId);
    List<Produto> findByDisponivelTrue();
    List<Produto> findByCategoria(String categoria);
}
