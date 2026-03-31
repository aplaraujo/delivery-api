package com.deliverytech.controllers;

import com.deliverytech.dto.request.LoginRequest;
import com.deliverytech.dto.request.RegisterRequest;
import com.deliverytech.entities.Role;
import com.deliverytech.entities.Usuario;
import com.deliverytech.repositories.UsuarioRepository;
import com.deliverytech.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest registerRequest) {
        if (usuarioRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("E-mail já cadastrado");
        }

        Usuario usuario = Usuario.builder()
                .email(registerRequest.getEmail())
                .senha(passwordEncoder.encode(registerRequest.getSenha()))
                .nome(registerRequest.getNome())
                .role(registerRequest.getRole() != null ? registerRequest.getRole() : Role.CLIENTE)
                .ativo(true)
                .restauranteId(registerRequest.getRestauranteId())
                .build();
        usuarioRepository.save(usuario);

        String token = jwtUtil.generateToken(User.withUsername(usuario.getEmail()).password(usuario.getSenha()).authorities("ROLE_" + usuario.getRole().name()).build(), usuario);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha()));
        Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        String token = jwtUtil.generateToken(User.withUsername(usuario.getEmail()).password(usuario.getSenha()).authorities("ROLE_" + usuario.getRole().name()).build(), usuario);
        return ResponseEntity.ok(token);
    }

}
