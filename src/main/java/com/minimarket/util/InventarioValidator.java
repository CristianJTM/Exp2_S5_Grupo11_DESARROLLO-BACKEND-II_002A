package com.minimarket.util;

import com.minimarket.entity.Inventario;
import com.minimarket.entity.Producto;

public class InventarioValidator {

    public boolean movimientoValido(
            Inventario inventario) {

        return inventario.getTipoMovimiento() != null
                && !inventario.getTipoMovimiento().isBlank()
                && inventario.getCantidad() != null;
    }

    public boolean productoCorrecto(
            Inventario inventario,
            Producto productoEsperado) {

        return inventario.getProducto()
                .equals(productoEsperado);
    }
}
