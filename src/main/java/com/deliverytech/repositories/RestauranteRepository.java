package com.deliverytech.repositories;

import com.deliverytech.entities.Restaurante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    List<Restaurante> findByCategoria(String categoria);
    // List<Restaurante> findByAtivoTrue();
    Page<Restaurante> findByAtivoTrue(Pageable pageable);
}
