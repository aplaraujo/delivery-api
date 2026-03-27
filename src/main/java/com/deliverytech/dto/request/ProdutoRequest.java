package com.deliverytech.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRequest {
    @NotBlank
    private String nome;

    @NotBlank
    private String categoria;

    @NotBlank
    private String descricao;

    @DecimalMin("0.1")
    @DecimalMax("500.0")
    private BigDecimal preco;

    private Long restauranteId;
}
