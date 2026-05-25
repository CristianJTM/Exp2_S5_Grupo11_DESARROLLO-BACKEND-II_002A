package com.minimarket.security.config;

import com.minimarket.security.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class    SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilita CSRF con la nueva sintaxis
                .authorizeHttpRequests(auth -> auth
                        // Público
                        .requestMatchers("/public/**").permitAll()
                        // GERENTE
                        .requestMatchers("/api/usuarios/**").hasAuthority("GERENTE")
                        .requestMatchers("/api/categorias/**").hasAuthority("GERENTE")
                        // EMPLEADO o GERENTE
                        .requestMatchers("/api/productos/**").hasAnyAuthority("EMPLEADO", "GERENTE")
                        .requestMatchers("/api/inventario/**").hasAnyAuthority("EMPLEADO", "GERENTE")
                        .requestMatchers("/api/ventas/**").hasAnyAuthority("EMPLEADO", "GERENTE")
                        .requestMatchers("/api/detalle-ventas/**").hasAnyAuthority("EMPLEADO", "GERENTE")
                        // Todos los roles
                        .requestMatchers("/api/carrito/**")
                        .hasAnyAuthority("CLIENTE", "EMPLEADO", "GERENTE")

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/public/hola", true) // Redirigir después del login
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/public/hola")
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Configuración de encriptación de contraseñas
    }
}
