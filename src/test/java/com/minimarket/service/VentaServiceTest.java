package com.minimarket.service;

import com.minimarket.entity.Usuario;
import com.minimarket.entity.Venta;
import com.minimarket.repository.VentaRepository;
import com.minimarket.service.impl.VentaServiceImpl;
import com.minimarket.util.VentaValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VentaServiceTest {

    @Mock
    private VentaRepository ventaRepository;

    @Mock
    private VentaValidator ventaValidator;

    @InjectMocks
    private VentaServiceImpl ventaService;

    @Test
    void findById_VentaExiste_DebeRetornarVenta() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);

        Venta venta = new Venta();
        venta.setId(1L);
        venta.setUsuario(usuario);
        venta.setFecha(new Date());

        when(ventaRepository.findById(1L))
                .thenReturn(Optional.of(venta));

        Venta resultado =
                ventaService.findById(1L);

        assertNotNull(resultado);
        assertEquals(1L,
                resultado.getId());

        verify(ventaRepository)
                .findById(1L);
    }

    @Test
    void findById_VentaNoExiste_DebeRetornarNull() {

        when(ventaRepository.findById(1L))
                .thenReturn(Optional.empty());

        Venta resultado =
                ventaService.findById(1L);

        assertNull(resultado);

        verify(ventaRepository)
                .findById(1L);
    }

    @Test
    void save_DebeGuardarVenta() {

        Venta venta = new Venta();

        Usuario usuario = new Usuario();

        usuario.setUsername("cristian");

        venta.setUsuario(usuario);

        when(ventaValidator.usuarioValido(any()))
                .thenReturn(true);

        when(ventaValidator.tieneStockSuficiente(any()))
                .thenReturn(true);

        when(ventaRepository.save(venta))
                .thenReturn(venta);

        Venta resultado =
                ventaService.save(venta);

        assertNotNull(resultado);

        verify(ventaRepository)
                .save(venta);
    }

    @Test
    void findByUsuarioId_DebeRetornarListaVentas() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);

        Venta venta = new Venta();
        venta.setId(1L);
        venta.setUsuario(usuario);

        List<Venta> ventas =
                List.of(venta);

        when(ventaRepository.findByUsuarioId(1L))
                .thenReturn(ventas);

        List<Venta> resultado =
                ventaService.findByUsuarioId(1L);

        assertEquals(1,
                resultado.size());

        verify(ventaRepository)
                .findByUsuarioId(1L);
    }

    @Test
    void findAll_DebeRetornarListaVentas() {

        Venta venta1 = new Venta();
        venta1.setId(1L);

        Venta venta2 = new Venta();
        venta2.setId(2L);

        when(ventaRepository.findAll())
                .thenReturn(List.of(
                        venta1,
                        venta2
                ));

        List<Venta> resultado =
                ventaService.findAll();

        assertEquals(2,
                resultado.size());

        verify(ventaRepository)
                .findAll();
    }

    @Test
    void save_UsuarioInvalido_DebeLanzarExcepcion() {

        Venta venta = new Venta();

        when(ventaValidator.usuarioValido(any()))
                .thenReturn(false);

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> ventaService.save(venta)
                );

        assertEquals(
                "Usuario inválido",
                exception.getMessage()
        );

        verify(ventaRepository, never())
                .save(any());
    }

    @Test
    void save_StockInsuficiente_DebeLanzarExcepcion() {

        Venta venta = new Venta();

        when(ventaValidator.usuarioValido(any()))
                .thenReturn(true);

        when(ventaValidator.tieneStockSuficiente(any()))
                .thenReturn(false);

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> ventaService.save(venta)
                );

        assertEquals(
                "Stock insuficiente",
                exception.getMessage()
        );

        verify(ventaRepository, never())
                .save(any());
    }
}