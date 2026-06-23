package com.minimarket.util;

import com.minimarket.entity.Carrito;
import com.minimarket.entity.Producto;
import com.minimarket.entity.Usuario;

public class CarritoValidator {

    public boolean tieneStockSuficiente(Carrito carrito) {

        Producto producto = carrito.getProducto();

        return producto.getStock() >= carrito.getCantidad();
    }

    public boolean usuarioCorrecto(Carrito carrito,
                                   Usuario usuarioEsperado) {

        return carrito.getUsuario()
                .equals(usuarioEsperado);
    }
}
