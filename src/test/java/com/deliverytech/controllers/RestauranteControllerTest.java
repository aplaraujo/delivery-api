package com.deliverytech.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(authorities = "ROLE_ADMIN")
class RestauranteControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void deveCriarRestauranteComSucesso() throws Exception {
        String json = "{\"nome\":\"Sabor da Vila\",\"categoria\":\"Comida Brasileira\",\"telefone\":\"+55 11 91234-5678\",\"taxaEntrega\":\"5.99\",\"tempoEntregaMinutos\":\"40\"}";

        mockMvc.perform(post("/api/restaurantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }
    // String json = "{\"nome\":\"Pizza Expressa\",\"categoria\":\"Pizzaria\",\"telefone\":\"+55 11 92345-6789\",\"taxaEntrega\":\"7.50\",\"tempoEntregaMinutos\":\"30\"}";
    @Test
    void naoDeveCriarRestauranteComNomeEmBranco() throws Exception {
        // Testa a validação @NotBlank no campo "nome"
        String json = "{\"nome\":\"\",\"categoria\":\"Pizzaria\",\"telefone\":\"+55 11 92345-6789\",\"taxaEntrega\":\"7.50\",\"tempoEntregaMinutos\":\"30\"}";

        mockMvc.perform(post("/api/restaurantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCriarRestauranteComNomeForaDoLimiteDe2A100Caracteres() throws Exception {
        // Tenta criar um restaurante com o nome fora do limite de 2 a 100 caracteres
        // A API deve retornar um erro 400 (Bad Request) por causa da anotação @Size
        String json = "{\"nome\":\"P\",\"categoria\":\"Pizzaria\",\"telefone\":\"+55 11 92345-6789\",\"taxaEntrega\":\"7.50\",\"tempoEntregaMinutos\":\"30\"}";

        mockMvc.perform(post("/api/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCriarRestauranteComCategoriaEmBranco() throws Exception {
        // Testa a validação @NotBlank no campo "categoria"
        String json = "{\"nome\":\"Pizza Expressa\",\"categoria\":\"\",\"telefone\":\"+55 11 92345-6789\",\"taxaEntrega\":\"7.50\",\"tempoEntregaMinutos\":\"30\"}";

        mockMvc.perform(post("/api/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCriarRestauranteComTelefoneEmBranco() throws Exception {
        // Testa a validação @NotBlank no campo "telefone"
        String json = "{\"nome\":\"Pizza Expressa\",\"categoria\":\"Pizzaria\",\"telefone\":\"\",\"taxaEntrega\":\"7.50\",\"tempoEntregaMinutos\":\"30\"}";

        mockMvc.perform(post("/api/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCriarRestauranteComTaxaDeEntregaNula() throws Exception {
        // Tenta criar um restaurante com a taxa de entrega nula
        // A API deve retornar um erro 400 (Bad Request) por causa da anotação @NotNull
        String json = "{\"nome\":\"Pizza Expressa\",\"categoria\":\"Pizzaria\",\"telefone\":\"+55 11 92345-6789\",\"taxaEntrega\":\"null\",\"tempoEntregaMinutos\":\"30\"}";

        mockMvc.perform(post("/api/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCriarRestauranteComTaxaDeEntregaComValorNegativo() throws Exception {
        // Tenta criar um restaurante com a taxa de entrega com valor negativo
        // A API deve retornar um erro 400 (Bad Request) por causa da anotação @DecimalMin
        String json = "{\"nome\":\"Pizza Expressa\",\"categoria\":\"Pizzaria\",\"telefone\":\"+55 11 92345-6789\",\"taxaEntrega\":\"-7.50\",\"tempoEntregaMinutos\":\"30\"}";

        mockMvc.perform(post("/api/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCriarRestauranteComTempoDeEntregaNulo() throws Exception {
        // Tenta criar um restaurante com o tempo de entrega nulo
        // A API deve retornar um erro 400 (Bad Request) por causa da anotação @NotNull
        String json = "{\"nome\":\"Pizza Expressa\",\"categoria\":\"Pizzaria\",\"telefone\":\"+55 11 92345-6789\",\"taxaEntrega\":\"7.50\",\"tempoEntregaMinutos\":\"null\"}";

        mockMvc.perform(post("/api/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                         .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCriarRestauranteComTempoDeEntregaInferiorA10Minutos() throws Exception {
        // Tenta criar um restaurante com o tempo de entrega inferior a 10 minutos
        // A API deve retornar um erro 400 (Bad Request) por causa da anotação @Min
        String json = "{\"nome\":\"Pizza Expressa\",\"categoria\":\"Pizzaria\",\"telefone\":\"+55 11 92345-6789\",\"taxaEntrega\":\"7.50\",\"tempoEntregaMinutos\":\"2\"}";

        mockMvc.perform(post("/api/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCriarRestauranteComTempoDeEntregaSuperiorA120Minutos() throws Exception {
        // Tenta criar um restaurante com o tempo de entrega superior a 120 minutos
        // A API deve retornar um erro 400 (Bad Request) por causa da anotação @Max
        String json = "{\"nome\":\"Pizza Expressa\",\"categoria\":\"Pizzaria\",\"telefone\":\"+55 11 92345-6789\",\"taxaEntrega\":\"7.50\",\"tempoEntregaMinutos\":\"200\"}";

        mockMvc.perform(post("/api/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

}