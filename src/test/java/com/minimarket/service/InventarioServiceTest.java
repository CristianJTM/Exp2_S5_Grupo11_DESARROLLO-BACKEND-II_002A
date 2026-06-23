package com.minimarket.service;

import com.minimarket.entity.Inventario;
import com.minimarket.repository.InventarioRepository;
import com.minimarket.service.impl.InventarioServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @InjectMocks
    private InventarioServiceImpl inventarioService;

    @Test
    void findAll_DebeRetornarLista() {

        Inventario inventario =
                new Inventario();

        when(inventarioRepository.findAll())
                .thenReturn(List.of(inventario));

        List<Inventario> resultado =
                inventarioService.findAll();

        assertEquals(
                1,
                resultado.size()
        );

        verify(inventarioRepository)
                .findAll();
    }

    @Test
    void findById_DebeRetornarInventario() {

        Inventario inventario =
                new Inventario();

        inventario.setId(1L);

        when(inventarioRepository.findById(1L))
                .thenReturn(
                        Optional.of(inventario)
                );

        Inventario resultado =
                inventarioService.findById(1L);

        assertNotNull(resultado);

        assertEquals(
                1L,
                resultado.getId()
        );

        verify(inventarioRepository)
                .findById(1L);
    }

    @Test
    void save_DebeGuardarInventario() {

        Inventario inventario =
                new Inventario();

        when(inventarioRepository
                .save(inventario))
                .thenReturn(inventario);

        Inventario resultado =
                inventarioService.save(
                        inventario
                );

        assertNotNull(resultado);

        verify(inventarioRepository)
                .save(inventario);
    }

    @Test
    void deleteById_DebeEliminarInventario() {

        inventarioService.deleteById(1L);

        verify(inventarioRepository)
                .deleteById(1L);
    }

    @Test
    void findByProductoId_DebeRetornarLista() {

        Inventario inventario =
                new Inventario();

        when(inventarioRepository
                .findByProductoId(1L))
                .thenReturn(
                        List.of(inventario)
                );

        List<Inventario> resultado =
                inventarioService
                        .findByProductoId(1L);

        assertEquals(
                1,
                resultado.size()
        );

        verify(inventarioRepository)
                .findByProductoId(1L);
    }
}
