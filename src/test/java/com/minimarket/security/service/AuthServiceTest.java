package com.minimarket.security.service;

import com.minimarket.entity.Rol;
import com.minimarket.entity.Usuario;
import com.minimarket.repository.RolRepository;
import com.minimarket.repository.UsuarioRepository;
import com.minimarket.security.model.AuthResponse;
import com.minimarket.security.model.LoginRequest;
import com.minimarket.security.model.RegisterRequest;
import com.minimarket.security.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void login_UsuarioValido_DebeRetornarToken() {

        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("1234");

        User userDetails = new User(
                "admin",
                "1234",
                java.util.Collections.emptyList()
        );

        when(userDetailsService.loadUserByUsername("admin"))
                .thenReturn(userDetails);

        when(jwtUtil.generateToken(userDetails))
                .thenReturn("token-jwt");

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("token-jwt", response.getToken());

        verify(authenticationManager)
                .authenticate(any());

        verify(jwtUtil)
                .generateToken(userDetails);
    }

    @Test
    void register_UsuarioNuevo_DebeGuardarUsuario() {

        RegisterRequest request = new RegisterRequest();

        request.setUsername("cliente");
        request.setPassword("1234");

        Rol rol = new Rol();
        rol.setNombre("CLIENTE");

        when(usuarioRepository.findByUsername("cliente"))
                .thenReturn(Optional.empty());

        when(rolRepository.findByNombre("CLIENTE"))
                .thenReturn(Optional.of(rol));

        when(passwordEncoder.encode("1234"))
                .thenReturn("passwordEncriptada");

        String resultado = authService.register(request);

        assertEquals(
                "Usuario registrado correctamente",
                resultado
        );

        verify(usuarioRepository)
                .save(any(Usuario.class));
    }

    @Test
    void register_UsuarioExistente_DebeRetornarMensaje() {

        RegisterRequest request = new RegisterRequest();

        request.setUsername("cliente");

        Usuario usuario = new Usuario();

        when(usuarioRepository.findByUsername("cliente"))
                .thenReturn(Optional.of(usuario));

        String resultado = authService.register(request);

        assertEquals(
                "El usuario ya existe",
                resultado
        );

        verify(usuarioRepository, never())
                .save(any());
    }

    @Test
    void login_CredencialesInvalidas_DebeLanzarExcepcion() {

        LoginRequest request = new LoginRequest();

        request.setUsername("admin");
        request.setPassword("1234");

        doThrow(new RuntimeException())
                .when(authenticationManager)
                .authenticate(any());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> authService.login(request)
                );

        assertEquals(
                "Usuario no encontrado o contraseña incorrecta",
                exception.getMessage()
        );
    }

    @Test
    void register_RolNoExiste_DebeLanzarExcepcion() {

        RegisterRequest request = new RegisterRequest();

        request.setUsername("cliente");
        request.setPassword("1234");

        when(usuarioRepository.findByUsername("cliente"))
                .thenReturn(Optional.empty());

        when(rolRepository.findByNombre("CLIENTE"))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> authService.register(request)
        );
    }
}
