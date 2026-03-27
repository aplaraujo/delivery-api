package com.deliverytech.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestauranteRequest {
    @NotBlank
    private String nome;

    @NotBlank
    private String categoria;

    @NotBlank
    private String telefone;

    @DecimalMin("0.0")
    private BigDecimal taxaEntrega;

    @Min(10)
    @Max(120)
    private Integer tempoEntregaMinutos;
}
