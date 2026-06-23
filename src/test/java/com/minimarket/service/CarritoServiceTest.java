package com.minimarket.service;

import com.minimarket.entity.Carrito;
import com.minimarket.repository.CarritoRepository;
import com.minimarket.service.impl.CarritoServiceImpl;
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
public class CarritoServiceTest {

    @Mock
    private CarritoRepository carritoRepository;

    @InjectMocks
    private CarritoServiceImpl carritoService;

    @Test
    void findAll_DebeRetornarLista() {

        Carrito carrito = new Carrito();

        when(carritoRepository.findAll())
                .thenReturn(List.of(carrito));

        List<Carrito> resultado =
                carritoService.findAll();

        assertEquals(1, resultado.size());

        verify(carritoRepository)
                .findAll();
    }

    @Test
    void findById_DebeRetornarCarrito() {

        Carrito carrito = new Carrito();

        carrito.setId(1L);

        when(carritoRepository.findById(1L))
                .thenReturn(Optional.of(carrito));

        Carrito resultado =
                carritoService.findById(1L);

        assertNotNull(resultado);

        assertEquals(
                1L,
                resultado.getId()
        );

        verify(carritoRepository)
                .findById(1L);
    }

    @Test
    void save_DebeGuardarCarrito() {

        Carrito carrito = new Carrito();

        when(carritoRepository.save(carrito))
                .thenReturn(carrito);

        Carrito resultado =
                carritoService.save(carrito);

        assertNotNull(resultado);

        verify(carritoRepository)
                .save(carrito);
    }

    @Test
    void deleteById_DebeEliminarCarrito() {

        carritoService.deleteById(1L);

        verify(carritoRepository)
                .deleteById(1L);
    }

    @Test
    void findByUsuarioId_DebeRetornarLista() {

        Carrito carrito = new Carrito();

        when(carritoRepository
                .findByUsuarioId(1L))
                .thenReturn(List.of(carrito));

        List<Carrito> resultado =
                carritoService.findByUsuarioId(1L);

        assertEquals(
                1,
                resultado.size()
        );

        verify(carritoRepository)
                .findByUsuarioId(1L);
    }
}
