package com.deliverytech.security;

import com.deliverytech.entities.Usuario;
import com.deliverytech.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Primary
@RequiredArgsConstructor
public class UsuarioDetailsServiceImpl implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario  = usuarioRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuário no encontrado"));

        return new User(
            usuario.getEmail(), usuario.getSenha(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + usuario.getRole())));
    }
}
