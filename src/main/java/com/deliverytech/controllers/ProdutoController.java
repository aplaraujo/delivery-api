package com.deliverytech.controllers;

import com.deliverytech.dto.request.ProdutoRequest;
import com.deliverytech.dto.response.ProdutoResponse;
import com.deliverytech.entities.Produto;
import com.deliverytech.entities.Restaurante;
import com.deliverytech.exception.EntityNotFoundException;
import com.deliverytech.services.ProdutoService;
import com.deliverytech.services.RestauranteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {
    private final ProdutoService produtoService;
    private final RestauranteService restauranteService;

    @PostMapping
    public ResponseEntity<ProdutoResponse> cadastrar(@Valid @RequestBody ProdutoRequest produtoRequest){
        Restaurante restaurante = restauranteService.buscarPorId(produtoRequest.getRestauranteId()).orElseThrow(() -> new EntityNotFoundException("Restaurante", produtoRequest.getRestauranteId()));

        Produto produto = Produto.builder()
                .nome(produtoRequest.getNome())
                .categoria(produtoRequest.getCategoria())
                .descricao(produtoRequest.getDescricao())
                .preco(produtoRequest.getPreco())
                .disponivel(true)
                .restaurante(restaurante)
                .build();

        Produto salvo = produtoService.cadastrar(produto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(salvo.getId()).toUri();

        return ResponseEntity.created(location).body(new ProdutoResponse(salvo.getId(), salvo.getNome(), salvo.getCategoria(), salvo.getDescricao(), salvo.getPreco(), salvo.getDisponivel()));
    }

    @GetMapping("/restaurante/{restauranteId}")
    public List<ProdutoResponse> buscarPorRestaurante(@PathVariable Long restauranteId){
        // Valida se o restaurante existe antes de mostrar a lista de produtos
        if (restauranteService.buscarPorId(restauranteId).isEmpty()){
            throw new EntityNotFoundException("Restaurante", restauranteId);
        }

        return produtoService.buscarPorRestaurante(restauranteId).stream()
                .map(produto -> new ProdutoResponse(produto.getId(), produto.getNome(), produto.getCategoria(), produto.getDescricao(), produto.getPreco(), produto.getDisponivel()))
                .toList();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoRequest produtoRequest){
        Produto atualizado = Produto.builder()
                .nome(produtoRequest.getNome())
                .categoria(produtoRequest.getCategoria())
                .descricao(produtoRequest.getDescricao())
                .preco(produtoRequest.getPreco())
                .build();

        Produto salvo = produtoService.atualizar(id, atualizado);
        return ResponseEntity.ok(new ProdutoResponse(salvo.getId(), salvo.getNome(), salvo.getCategoria(), salvo.getDescricao(), salvo.getPreco(), salvo.getDisponivel()));
    }

    @PatchMapping("/{id}/disponibilidade")
    public ResponseEntity<Void> alterarDisponibilidade(@PathVariable Long id, @RequestParam boolean disponivel){
        produtoService.alterarDisponibilidade(id, disponivel);
        return ResponseEntity.noContent().build();
    }
}
