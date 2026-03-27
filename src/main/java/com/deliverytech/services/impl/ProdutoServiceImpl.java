package com.deliverytech.services.impl;

import com.deliverytech.entities.Produto;
import com.deliverytech.repositories.ProdutoRepository;
import com.deliverytech.services.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {
    private final ProdutoRepository produtoRepository;

    @Override
    public Produto cadastrar(Produto produto) {
        return produtoRepository.save(produto);
    }

    @Override
    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    @Override
    public List<Produto> buscarPorRestaurante(Long restauranteId) {
        return produtoRepository.findByRestauranteId(restauranteId);
    }

    @Override
    public Produto atualizar(Long id, Produto produtoAtualizado) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setNome(produtoAtualizado.getNome());
                    produto.setDescricao(produtoAtualizado.getDescricao());
                    produto.setCategoria(produtoAtualizado.getCategoria());
                    produto.setPreco(produtoAtualizado.getPreco());
                    return produtoRepository.save(produto);
                }).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    @Override
    public void alterarDisponibilidade(Long id, boolean disponivel) {
        produtoRepository.findById(id).ifPresent(produto -> {
            produto.setDisponivel(disponivel);
            produtoRepository.save(produto);
        });
    }
}
