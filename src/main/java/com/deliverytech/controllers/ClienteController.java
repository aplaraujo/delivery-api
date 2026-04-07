package com.deliverytech.controllers;

import com.deliverytech.dto.request.ClienteRequest;
import com.deliverytech.dto.response.ClienteResponse;
import com.deliverytech.entities.Cliente;
import com.deliverytech.services.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {
    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponse> cadastrar(@Valid @RequestBody ClienteRequest clienteRequest) {
        logger.info("Cadastro de cliente iniciado: {}", clienteRequest.getEmail());

        Cliente cliente = Cliente.builder()
                .nome(clienteRequest.getNome())
                .email(clienteRequest.getEmail())
                .ativo(true)
                .build();

        Cliente salvo = clienteService.cadastrar(cliente);

        logger.debug("Cliente salvo com id: {}", salvo.getId());

        return ResponseEntity.ok(new ClienteResponse(salvo.getId(),  salvo.getNome(), salvo.getEmail(), salvo.getAtivo()));
    }

    @GetMapping
    public Page<ClienteResponse> buscar(Pageable pageable) {
        logger.info("Buscando todos os clientes ativos de forma paginada");
        Page<Cliente> clientesPaginados = clienteService.buscarPorAtivos(pageable);
        return clientesPaginados.map(cliente -> new ClienteResponse(cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getAtivo()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> buscar(@PathVariable Long id) {
        logger.info("Buscando o cliente com id: {}", id);
        return clienteService.buscarPorId(id)
                .map(cliente -> new ClienteResponse(cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getAtivo()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Cliente com ID {} não encontrado", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> atualizar(@PathVariable Long id, @Valid @RequestBody ClienteRequest clienteRequest) {
        logger.info("Atualizando o cliente com id: {}", id);

        Cliente atualizado = Cliente.builder()
                .nome(clienteRequest.getNome())
                .email(clienteRequest.getEmail())
                .build();

        Cliente salvo = clienteService.atualizar(id, atualizado);

        return ResponseEntity.ok(new ClienteResponse(salvo.getId(), salvo.getNome(), salvo.getEmail(), salvo.getAtivo()));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> ativarDesativar(@PathVariable Long id) {
        logger.info("Alterando o cliente com id: {}", id);
        clienteService.ativarDesativar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status")
    public ResponseEntity<String> status() {
        logger.debug("Status endpoint acessado");
        int cpuCores = Runtime.getRuntime().availableProcessors();
        logger.info("CPU Cores disponíveis: {}", cpuCores);
        return ResponseEntity.ok("API está online");
    }
}
