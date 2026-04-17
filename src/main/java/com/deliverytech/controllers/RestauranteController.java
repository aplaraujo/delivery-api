package com.deliverytech.controllers;

import com.deliverytech.dto.request.RestauranteRequest;
import com.deliverytech.dto.response.RestauranteResponse;
import com.deliverytech.entities.Restaurante;
import com.deliverytech.exception.EntityNotFoundException;
import com.deliverytech.services.RestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/restaurantes")
@RequiredArgsConstructor
@Tag(name = "Restaurante", description = "Endpoints para gerenciamento de restaurantes")
public class RestauranteController {
    private final RestauranteService restauranteService;

    @Operation(summary = "Cadastra um novo restaurante", description = "Cria um novo restaurante no sistema")
    @PostMapping
    public ResponseEntity<RestauranteResponse> cadastrar(@Valid @RequestBody RestauranteRequest restauranteRequest){
        Restaurante restaurante = Restaurante.builder()
                .nome(restauranteRequest.getNome())
                .telefone(restauranteRequest.getTelefone())
                .categoria(restauranteRequest.getCategoria())
                .taxaEntrega(restauranteRequest.getTaxaEntrega())
                .tempoEntregaMinutos(restauranteRequest.getTempoEntregaMinutos())
                .ativo(true)
                .build();

        Restaurante salvo = restauranteService.cadastrar(restaurante);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(salvo.getId()).toUri();

        return ResponseEntity.created(location).body(new RestauranteResponse(
                salvo.getId(), salvo.getNome(), salvo.getCategoria(), salvo.getTelefone(), salvo.getTaxaEntrega(), salvo.getTempoEntregaMinutos(), salvo.getAtivo()
        ));
    }

    @Operation(summary = "Busca todos os restaurantes", description = "Retorna uma lista paginada de todos os restaurantes")
    @GetMapping
    public Page<RestauranteResponse> buscarTodos(Pageable pageable) {
        Page<Restaurante> restaurantesPaginados = restauranteService.buscarTodos(pageable);
        return restaurantesPaginados.map(rest -> new RestauranteResponse(rest.getId(), rest.getNome(), rest.getCategoria(), rest.getTelefone(), rest.getTaxaEntrega(), rest.getTempoEntregaMinutos(), rest.getAtivo()));
    }

    @Operation(summary = "Busca um restaurante por ID", description = "Retorna os detalhes de um restaurante específico pelo seu ID")
    @GetMapping("/{id}")
    public ResponseEntity<RestauranteResponse> buscarPorId(@PathVariable Long id){
        return restauranteService.buscarPorId(id)
                .map(rest -> new RestauranteResponse(
                        rest.getId(), rest.getNome(), rest.getCategoria(), rest.getTelefone(), rest.getTaxaEntrega(), rest.getTempoEntregaMinutos(), rest.getAtivo()
                )).map(ResponseEntity::ok).orElseThrow(() -> new EntityNotFoundException("Restaurante", id));
    }

    @Operation(summary = "Busca restaurantes por categoria", description = "Retorna uma lista de restaurantes que pertencem a uma categoria específica")
    @GetMapping("/categoria/{categoria}")
    public List<RestauranteResponse> buscarPorCategoria(@PathVariable String categoria){
        return restauranteService.buscarPorCategoria(categoria).stream()
                .map(rest -> new RestauranteResponse(
                        rest.getId(), rest.getNome(), rest.getCategoria(), rest.getTelefone(), rest.getTaxaEntrega(), rest.getTempoEntregaMinutos(), rest.getAtivo()
                )).toList();
    }

    @Operation(summary = "Atualiza um restaurante", description = "Atualiza os dados de um restaurante a partir do seu ID")
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
