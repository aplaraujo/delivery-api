package com.deliverytech.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(authorities = "ROLE_CLIENTE")
class ClienteControllerTest {
    @Autowired
    MockMvc mockMvc;

    Long existingClientId;

    @BeforeEach
    void setUp() {
       existingClientId = 1L;
    }

    @Test
    void deveCriarClienteComSucesso() throws Exception {
        String json = "{\"nome\":\"Mariazinha\",\"email\":\"mariazinha@gmail.com\"}";

        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void naoDeveCriarClienteComNomeEmBranco() throws Exception {
        // Testa a validação @NotBlank no campo "nome
        String json = "{\"nome\":\"\",\"email\":\"mariazinha@gmail.com\"}";

        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCriarClienteComEmailEmBranco() throws Exception {
        // Tenta criar um cliente com o nome em branco
        // A API deve retornar um erro 400 (Bad Request) por causa da anotação @NotBlank
        String json = "{\"nome\":\"Mariazinha\",\"email\":\"\"}";

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCriarClienteComEmailInvalido() throws Exception {
        // Tenta criar um cliente com o e-mail em formato inválido
        // A API deve retornar um erro 400 (Bad Request) por causa da anotação @Email
        String json = "{\"nome\":\"Mariazinha\",\"email\":\"mariazinha.com\"}";

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornarListaDeClientesOrdenadaPorNomeEPorStatusAtivo() throws Exception {
        mockMvc.perform(get("/api/clientes")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content[0].nome").value("Caroline Nair da Costa"))
                .andExpect(jsonPath("$.content[1].nome").value("Giovanni Henrique Cavalcanti"))
                .andExpect(jsonPath("$.content[2].nome").value("Hugo Vitor Martins"));
    }

    @Test
    void deveRetornarBuscaDeClientePeloId() throws Exception {
        mockMvc.perform(get("/api/clientes/" + existingClientId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingClientId))
                .andExpect(jsonPath("$.nome").value("Caroline Nair da Costa"))
                .andExpect(jsonPath("$.email").value("carolinenairdacosta@outllok.com"))
                .andExpect(jsonPath("$.ativo").value(true));
    }

    @Test
    void deveAtualizarUmClienteComSucesso() throws Exception {
        String json = "{\"nome\":\"Caroline N. da Costa\",\"email\":\"carolinenairdacosta@outllok.com\", \"ativo\":\"true\"}";

        mockMvc.perform(put("/api/clientes/" + existingClientId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    // String json = "{\"nome\":\"Hugo Vitor Martins\",\"email\":\"hugovitormartins@iclud.com\", \"ativo\":\"true\"}";
    @Test
    void naoDeveAtualizarClienteComNomeEmBranco() throws Exception {
        Long clientId = 2L;
        String json = "{\"nome\":\"\",\"email\":\"hugovitormartins@iclud.com\", \"ativo\":\"true\"}";

        mockMvc.perform(put("/api/clientes/" + clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveAtualizarClienteComEmailEmBranco() throws Exception {
        Long clientId = 2L;
        String json = "{\"nome\":\"Hugo Vitor Martins\",\"email\":\"\", \"ativo\":\"true\"}";

        mockMvc.perform(put("/api/clientes/" + clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveAtualizarClienteComEmailInvalido() throws Exception {
        Long clientId = 2L;
        String json = "{\"nome\":\"Hugo Vitor Martins\",\"email\":\"hugovitormartinsiclud.com\", \"ativo\":\"true\"}";

        mockMvc.perform(put("/api/clientes/" + clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isBadRequest());
    }

    @Test
    void deveAlterarOStatusDoClienteComSucesso() throws Exception {
        String json = "{\"nome\":\"Caroline Nair da Costa\",\"email\":\"carolinenairdacosta@outllok.com\", \"ativo\":\"false\"}";

        mockMvc.perform(patch("/api/clientes/" + existingClientId + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNoContent());
    }
}