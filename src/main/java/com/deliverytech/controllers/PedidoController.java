package com.deliverytech.controllers;

import com.deliverytech.dto.request.PedidoRequest;
import com.deliverytech.dto.response.ItemPedidoResponse;
import com.deliverytech.dto.response.PedidoResponse;
import com.deliverytech.entities.Cliente;
import com.deliverytech.entities.ItemPedido;
import com.deliverytech.entities.Pedido;
import com.deliverytech.entities.Produto;
import com.deliverytech.entities.Restaurante;
import com.deliverytech.entities.StatusPedido;
import com.deliverytech.services.ClienteService;
import com.deliverytech.services.PedidoService;
import com.deliverytech.services.ProdutoService;
import com.deliverytech.services.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {
    private final PedidoService pedidoService;
    private final ClienteService clienteService;
    private final RestauranteService restauranteService;
    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<PedidoResponse> criar(@RequestBody PedidoRequest pedidoRequest){
        Cliente cliente = clienteService.buscarPorId(pedidoRequest.getClienteId()).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        Restaurante restaurante = restauranteService.buscarPorId(pedidoRequest.getRestauranteId()).orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        List<ItemPedido> itens = pedidoRequest.getItens().stream().map(item -> {
            Produto produto = produtoService.buscarPorId(item.getProdutoId()).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
            return ItemPedido.builder()
                    .produto(produto)
                    .quantidade(item.getQuantidade())
                    .precoUnitario(produto.getPreco())
                    .build();
        }).toList();

        BigDecimal total = itens.stream().map(item -> item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade()))).reduce(BigDecimal.ZERO, BigDecimal::add);

        Pedido pedido = Pedido.builder()
                .cliente(cliente)
                .restaurante(restaurante)
                .status(StatusPedido.CRIADO)
                .total(total)
                .enderecoEntrega(pedidoRequest.getEnderecoEntrega())
                .itens(itens)
                .build();

        Pedido salvo = pedidoService.criar(pedido);
        List<ItemPedidoResponse> itensResp = salvo.getItens().stream()
                .map(item -> new ItemPedidoResponse(item.getProduto().getId(), item.getProduto().getNome(), item.getQuantidade(), item.getPrecoUnitario()))
                .toList();

        return ResponseEntity.ok(
                new PedidoResponse(
                        salvo.getId(),
                        cliente.getId(),
                        restaurante.getId(),
                        salvo.getEnderecoEntrega(),
                        salvo.getTotal(),
                        salvo.getStatus(),
                        salvo.getDataPedido(),
                        itensResp
                )
        );
    }
}
