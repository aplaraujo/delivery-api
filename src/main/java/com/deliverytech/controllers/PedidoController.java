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
import com.deliverytech.exception.EntityNotFoundException;
import com.deliverytech.services.ClienteService;
import com.deliverytech.services.PedidoService;
import com.deliverytech.services.ProdutoService;
import com.deliverytech.services.RestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Endpoints para gerenciamento de pedidos")
public class PedidoController {
    private final PedidoService pedidoService;
    private final ClienteService clienteService;
    private final RestauranteService restauranteService;
    private final ProdutoService produtoService;

    @Operation(summary = "Cria um novo pedido", description = "Cria um novo pedido em um restaurante específico")
    @PostMapping
    public ResponseEntity<PedidoResponse> criar(@Valid @RequestBody PedidoRequest pedidoRequest){
        Cliente cliente = clienteService.buscarPorId(pedidoRequest.getClienteId()).orElseThrow(() -> new EntityNotFoundException("Cliente", pedidoRequest.getClienteId()));
        Restaurante restaurante = restauranteService.buscarPorId(pedidoRequest.getRestauranteId()).orElseThrow(() -> new EntityNotFoundException("Restaurante", pedidoRequest.getRestauranteId()));

        List<ItemPedido> itens = pedidoRequest.getItens().stream().map(item -> {
            Produto produto = produtoService.buscarPorId(item.getProdutoId()).orElseThrow(() -> new EntityNotFoundException("Produto", item.getProdutoId()));
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

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(salvo.getId()).toUri();

        PedidoResponse pedidoResponse = new PedidoResponse(
                salvo.getId(),
                cliente.getId(),
                restaurante.getId(),
                salvo.getEnderecoEntrega(),
                salvo.getTotal(),
                salvo.getStatus(),
                salvo.getDataPedido(),
                itensResp
        );
        return ResponseEntity.created(location).body(pedidoResponse);
    }
}
