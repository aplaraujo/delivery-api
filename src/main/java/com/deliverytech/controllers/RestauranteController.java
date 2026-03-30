package com.deliverytech.controllers;

import com.deliverytech.dto.request.RestauranteRequest;
import com.deliverytech.dto.response.RestauranteResponse;
import com.deliverytech.entities.Restaurante;
import com.deliverytech.services.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/restaurantes")
@RequiredArgsConstructor
public class RestauranteController {
    private final RestauranteService restauranteService;

    @PostMapping
    public ResponseEntity<RestauranteResponse> cadastrar(@RequestBody RestauranteRequest restauranteRequest){
        Restaurante restaurante = Restaurante.builder()
                .nome(restauranteRequest.getNome())
                .telefone(restauranteRequest.getTelefone())
                .categoria(restauranteRequest.getCategoria())
                .taxaEntrega(restauranteRequest.getTaxaEntrega())
                .tempoEntregaMinutos(restauranteRequest.getTempoEntregaMinutos())
                .ativo(true)
                .build();

        Restaurante salvo = restauranteService.cadastrar(restaurante);

        return ResponseEntity.ok(new RestauranteResponse(
                salvo.getId(), salvo.getNome(), salvo.getCategoria(), salvo.getTelefone(), salvo.getTaxaEntrega(), salvo.getTempoEntregaMinutos(), salvo.getAtivo()
        ));
    }

    @GetMapping
    public List<RestauranteResponse> buscarTodos(){
        return restauranteService.buscarTodos().stream()
                .map(rest -> new RestauranteResponse(
                        rest.getId(), rest.getNome(), rest.getCategoria(), rest.getTelefone(), rest.getTaxaEntrega(), rest.getTempoEntregaMinutos(), rest.getAtivo()
                )).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestauranteResponse> buscarPorId(@PathVariable Long id){
        return restauranteService.buscarPorId(id)
                .map(rest -> new RestauranteResponse(
                        rest.getId(), rest.getNome(), rest.getCategoria(), rest.getTelefone(), rest.getTaxaEntrega(), rest.getTempoEntregaMinutos(), rest.getAtivo()
                )).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/categoria/{categoria}")
    public List<RestauranteResponse> buscarPorCategoria(@PathVariable String categoria){
        return restauranteService.buscarPorCategoria(categoria).stream()
                .map(rest -> new RestauranteResponse(
                        rest.getId(), rest.getNome(), rest.getCategoria(), rest.getTelefone(), rest.getTaxaEntrega(), rest.getTempoEntregaMinutos(), rest.getAtivo()
                )).toList();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestauranteResponse>  atualizar(@PathVariable Long id, @RequestBody RestauranteRequest restauranteRequest){
        Restaurante atualizado = Restaurante.builder()
                .nome(restauranteRequest.getNome())
                .telefone(restauranteRequest.getTelefone())
                .categoria(restauranteRequest.getCategoria())
                .taxaEntrega(restauranteRequest.getTaxaEntrega())
                .tempoEntregaMinutos(restauranteRequest.getTempoEntregaMinutos())
                .build();

        Restaurante salvo = restauranteService.atualizar(id, atualizado);
        return ResponseEntity.ok(new RestauranteResponse(
                salvo.getId(), salvo.getNome(), salvo.getCategoria(), salvo.getTelefone(), salvo.getTaxaEntrega(), salvo.getTempoEntregaMinutos(), salvo.getAtivo()
        ));
    }
}
