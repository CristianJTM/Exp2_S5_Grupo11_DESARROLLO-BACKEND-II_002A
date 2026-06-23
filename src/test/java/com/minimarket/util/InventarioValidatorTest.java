package com.minimarket.util;

import com.minimarket.entity.Inventario;
import com.minimarket.entity.Producto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventarioValidatorTest {

    private final InventarioValidator validator =
            new InventarioValidator();

    @Test
    void movimientoValido_DebeRetornarTrue() {

        Inventario inventario =
                new Inventario();

        inventario.setTipoMovimiento(
                "Entrada");

        inventario.setCantidad(10);

        assertTrue(
                validator.movimientoValido(
                        inventario
                )
        );
    }

    @Test
    void movimientoSinTipo_DebeRetornarFalse() {

        Inventario inventario =
                new Inventario();

        inventario.setCantidad(10);

        assertFalse(
                validator.movimientoValido(
                        inventario
                )
        );
    }

    @Test
    void productoCorrecto_DebeRetornarTrue() {

        Producto producto =
                new Producto();

        Inventario inventario =
                new Inventario();

        inventario.setProducto(producto);

        assertTrue(
                validator.productoCorrecto(
                        inventario,
                        producto
                )
        );
    }

    @Test
    void productoIncorrecto_DebeRetornarFalse() {

        Producto producto1 =
                new Producto();

        Producto producto2 =
                new Producto();

        Inventario inventario =
                new Inventario();

        inventario.setProducto(producto1);

        assertFalse(
                validator.productoCorrecto(
                        inventario,
                        producto2
                )
        );
    }
}
