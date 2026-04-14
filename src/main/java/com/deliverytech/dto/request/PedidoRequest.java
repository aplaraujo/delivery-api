package com.deliverytech.dto.request;

import com.deliverytech.entities.Endereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequest {
    @NotNull(message = "O ID do cliente é obrigatório")
    private Long clienteId;

    @NotNull(message = "O ID do restaurante é obrigatório")
    private Long restauranteId;

    @NotNull(message = "O endereço de entrega é obrigatório")
    @Valid
    private Endereco enderecoEntrega;

    @NotEmpty(message = "O pedido deve ter pelo menos um item")
    @Valid
    private List<ItemPedidoRequest> itens;
}
