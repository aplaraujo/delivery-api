package com.deliverytech.config;

import com.deliverytech.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/auth/**").permitAll()
                                // Enpoints públicos
                                .requestMatchers("/health","/info","/swagger-ui.html","/swagger-ui/**", "/api-docs/**", "/h2-console/**", "/actuator/**").permitAll()
                                // Endpoints de Cliente: ADMIN pode gerenciar, CLIENTE pode se cadastrar
                                .requestMatchers("/api/clientes").hasAnyAuthority("ROLE_ADMIN", "ROLE_CLIENTE")
                                .requestMatchers("/api/clientes/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_CLIENTE")
                                // Endpoints de Pedido: Apenas CLIENTE pode criar/ver
                                .requestMatchers("/api/pedidos").hasAuthority("ROLE_CLIENTE")
                                .requestMatchers("/api/pedidos/**").hasAuthority("ROLE_CLIENTE")
                                // Endpoints de Restaurante e Produto: Apenas ADMIN pode gerenciar
                                .requestMatchers("/api/restaurantes").hasAuthority("ROLE_ADMIN")
                                .requestMatchers("/api/restaurantes/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers("/api/produtos").hasAuthority("ROLE_ADMIN")
                                .requestMatchers("/api/produtos/**").hasAuthority("ROLE_ADMIN")
                                // Garante que qualquer outra requisição não listada seja bloqueada
                                .anyRequest().authenticated()

                        )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Adiciona nosso filtro JWT antes do filtro padrão
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}
