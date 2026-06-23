package com.minimarket.util;

import com.minimarket.entity.Carrito;
import com.minimarket.entity.Producto;
import com.minimarket.entity.Usuario;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CarritoValidatorTest {

    private final CarritoValidator validator =
            new CarritoValidator();

    @Test
    void stockSuficiente_DebeRetornarTrue() {

        Producto producto = new Producto();
        producto.setStock(20);

        Carrito carrito = new Carrito();
        carrito.setProducto(producto);
        carrito.setCantidad(5);

        assertTrue(
                validator.tieneStockSuficiente(carrito)
        );
    }

    @Test
    void stockInsuficiente_DebeRetornarFalse() {

        Producto producto = new Producto();
        producto.setStock(2);

        Carrito carrito = new Carrito();
        carrito.setProducto(producto);
        carrito.setCantidad(5);

        assertFalse(
                validator.tieneStockSuficiente(carrito)
        );
    }

    @Test
    void usuarioCorrecto_DebeRetornarTrue() {

        Usuario usuario = new Usuario();

        Carrito carrito = new Carrito();
        carrito.setUsuario(usuario);

        assertTrue(
                validator.usuarioCorrecto(
                        carrito,
                        usuario
                )
        );
    }

    @Test
    void usuarioIncorrecto_DebeRetornarFalse() {

        Usuario usuario1 = new Usuario();
        Usuario usuario2 = new Usuario();

        Carrito carrito = new Carrito();
        carrito.setUsuario(usuario1);

        assertFalse(
                validator.usuarioCorrecto(
                        carrito,
                        usuario2
                )
        );
    }
}
