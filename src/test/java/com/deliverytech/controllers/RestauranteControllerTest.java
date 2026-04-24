package com.deliverytech.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    @Test
    void deveRetornarListaDeRestaurantesOrdenadaPorNomeEPorStatusAtivo() throws Exception {
        mockMvc.perform(get("/api/restaurantes")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.content").exists())
                        .andExpect(jsonPath("$.content[0].nome").value("Burger Street"))
                        .andExpect(jsonPath("$.content[1].nome").value("Cantina do Nono"))
                        .andExpect(jsonPath("$.content[2].nome").value("Churrascaria Gaúcha"));
    }

    @Test
    void deveBuscarUmRestaurantePorId() throws Exception {
        Long existingRestaurantId = 4L;

        mockMvc.perform(get("/api/restaurantes/" + existingRestaurantId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingRestaurantId))
                .andExpect(jsonPath("$.nome").value("Burger Street"))
                .andExpect(jsonPath("$.categoria").value("Lanches"))
                .andExpect(jsonPath("$.telefone").value("(21) 97788-9900"))
                .andExpect(jsonPath("$.taxaEntrega").value(4.99))
                .andExpect(jsonPath("$.tempoEntregaMinutos").value(25))
                .andExpect(jsonPath("$.ativo").value(true));
    }

    @Test
    void deveBuscarUmRestaurantePorCategoria() throws Exception {
        String categoria = "Lanches";

        mockMvc.perform(get("/api/restaurantes/categoria/" + categoria)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // String json = "{\"nome\":\"Pizza Expressa\",\"categoria\":\"Pizzaria\",\"telefone\":\"+55 11 92345-6789\",\"taxaEntrega\":\"7.50\",\"tempoEntregaMinutos\":\"30\"}";
    @Test
    void deveAtualizarUmRestauranteComSucesso() throws Exception {
        Long existingRestaurantId = 1L;
        String json = "{\"nome\":\"Cantina do Nono Antonio\",\"categoria\":\"Italiana\",\"telefone\":\"(11) 91234-5678\",\"taxaEntrega\":\"5.99\",\"tempoEntregaMinutos\":\"40\",\"ativo\":\"true\"}";

        mockMvc.perform(put("/api/restaurantes/" + existingRestaurantId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    // String json = "{\"nome\":\"Sabor Oriental\",\"categoria\":\"Japonesa\",\"telefone\":\"(11) 98765-4321\",\"taxaEntrega\":\"7.50\",\"tempoEntregaMinutos\":\"50\",\"ativo\":\"true\"}";
    @Test
    void naoDeveAtualizarRestauranteComNomeEmBranco() throws Exception {
        // Testa a validação @NotBlank no campo "nome"
        Long existingRestaurantId = 2L;
        String json = "{\"nome\":\"\",\"categoria\":\"Japonesa\",\"telefone\":\"(11) 98765-4321\",\"taxaEntrega\":\"7.50\",\"tempoEntregaMinutos\":\"50\",\"ativo\":\"true\"}";

        mockMvc.perform(put("/api/restaurantes/" + existingRestaurantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveAtualizarRestauranteForaDoLimiteDe2A100Caracteres() throws Exception {
        Long existingRestaurantId = 2L;
        String json = "{\"nome\":\"S\",\"categoria\":\"Japonesa\",\"telefone\":\"(11) 98765-4321\",\"taxaEntrega\":\"7.50\",\"tempoEntregaMinutos\":\"50\",\"ativo\":\"true\"}";

        mockMvc.perform(put("/api/restaurantes/" + existingRestaurantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveAtualizarRestauranteComCategoriaEmBranco() throws Exception {
        // Testa a validação @NotBlank no campo "categoria"
        Long existingRestaurantId = 2L;
        String json = "{\"nome\":\"Sabor Oriental\",\"categoria\":\"\",\"telefone\":\"(11) 98765-4321\",\"taxaEntrega\":\"7.50\",\"tempoEntregaMinutos\":\"50\",\"ativo\":\"true\"}";

        mockMvc.perform(put("/api/restaurantes/" + existingRestaurantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveAtualizarRestauranteComTelefoneEmBranco() throws Exception {
        Long existingRestaurantId = 2L;
        String json = "{\"nome\":\"Sabor Oriental\",\"categoria\":\"Japonesa\",\"telefone\":\"\",\"taxaEntrega\":\"7.50\",\"tempoEntregaMinutos\":\"50\",\"ativo\":\"true\"}";

        mockMvc.perform(put("/api/restaurantes/" + existingRestaurantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveAtualizarRestauranteComTaxaDeEntregaNula() throws Exception {
        Long existingRestaurantId = 2L;
        String json = "{\"nome\":\"Sabor Oriental\",\"categoria\":\"Japonesa\",\"telefone\":\"\",\"taxaEntrega\":\"null\",\"tempoEntregaMinutos\":\"50\",\"ativo\":\"true\"}";

        mockMvc.perform(put("/api/restaurantes/" + existingRestaurantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveAtualizarRestauranteComTaxaDeEntregaComValorNegativo() throws Exception {
        Long existingRestaurantId = 2L;
        String json = "{\"nome\":\"Sabor Oriental\",\"categoria\":\"Japonesa\",\"telefone\":\"\",\"taxaEntrega\":\"-7.50\",\"tempoEntregaMinutos\":\"50\",\"ativo\":\"true\"}";

        mockMvc.perform(put("/api/restaurantes/" + existingRestaurantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveAtualizarRestauranteComTempoDeEntregaNulo() throws Exception {
        Long existingRestaurantId = 2L;
        String json = "{\"nome\":\"Sabor Oriental\",\"categoria\":\"Japonesa\",\"telefone\":\"(11) 98765-4321\",\"taxaEntrega\":\"7.50\",\"tempoEntregaMinutos\":\"null\",\"ativo\":\"true\"}";

        mockMvc.perform(put("/api/restaurantes/" + existingRestaurantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveAtualizarRestauranteComTempoDeEntregaInferiorA10Minutos() throws Exception {
        Long existingRestaurantId = 2L;
        String json = "{\"nome\":\"Sabor Oriental\",\"categoria\":\"Japonesa\",\"telefone\":\"(11) 98765-4321\",\"taxaEntrega\":\"7.50\",\"tempoEntregaMinutos\":\"5\",\"ativo\":\"true\"}";

        mockMvc.perform(put("/api/restaurantes/" + existingRestaurantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveAtualizarRestauranteComTempoDeEntregaSuperiorA120Minutos() throws Exception {
        Long existingRestaurantId = 2L;
        String json = "{\"nome\":\"Sabor Oriental\",\"categoria\":\"Japonesa\",\"telefone\":\"(11) 98765-4321\",\"taxaEntrega\":\"7.50\",\"tempoEntregaMinutos\":\"200\",\"ativo\":\"true\"}";

        mockMvc.perform(put("/api/restaurantes/" + existingRestaurantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isBadRequest());
    }
}