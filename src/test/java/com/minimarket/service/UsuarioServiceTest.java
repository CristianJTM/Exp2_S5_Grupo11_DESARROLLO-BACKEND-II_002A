package com.minimarket.service;

import com.minimarket.entity.Usuario;
import com.minimarket.repository.UsuarioRepository;
import com.minimarket.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Test
    void findById_UsuarioExiste_DebeRetornarUsuario() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("cliente");

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado =
                usuarioService.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("cliente",
                resultado.get().getUsername());

        verify(usuarioRepository)
                .findById(1L);
    }

    @Test
    void findById_UsuarioNoExiste_DebeRetornarEmpty() {

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.empty());

        Optional<Usuario> resultado =
                usuarioService.findById(1L);

        assertFalse(resultado.isPresent());

        verify(usuarioRepository)
                .findById(1L);
    }

    @Test
    void save_DebeGuardarUsuario() {

        Usuario usuario = new Usuario();
        usuario.setUsername("nuevoUsuario");

        when(usuarioRepository.save(usuario))
                .thenReturn(usuario);

        Usuario resultado =
                usuarioService.save(usuario);

        assertNotNull(resultado);
        assertEquals("nuevoUsuario",
                resultado.getUsername());

        verify(usuarioRepository)
                .save(usuario);
    }

    @Test
    void findByUsername_DebeRetornarUsuario() {

        Usuario usuario = new Usuario();
        usuario.setUsername("cliente");

        when(usuarioRepository.findByUsername("cliente"))
                .thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado =
                usuarioService.findByUsername("cliente");

        assertTrue(resultado.isPresent());
        assertEquals("cliente",
                resultado.get().getUsername());

        verify(usuarioRepository)
                .findByUsername("cliente");
    }

    @Test
    void deleteById_DebeEliminarUsuario() {

        usuarioService.deleteById(1L);

        verify(usuarioRepository)
                .deleteById(1L);
    }
}