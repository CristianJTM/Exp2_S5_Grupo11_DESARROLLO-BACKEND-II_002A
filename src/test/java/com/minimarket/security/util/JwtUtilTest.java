package com.minimarket.security.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;

import java.lang.reflect.Field;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() throws Exception {

        jwtUtil = new JwtUtil();

        Field secret =
                JwtUtil.class.getDeclaredField("secret");

        secret.setAccessible(true);

        secret.set(
                jwtUtil,
                "12345678901234567890123456789012"
        );

        jwtUtil.init();
    }

    @Test
    void generateToken_DebeCrearToken() {

        User usuario = new User(
                "admin",
                "1234",
                Collections.emptyList()
        );

        String token =
                jwtUtil.generateToken(usuario);

        assertNotNull(token);
    }

    @Test
    void extractUsername_DebeRetornarUsername() {

        User usuario = new User(
                "admin",
                "1234",
                Collections.emptyList()
        );

        String token =
                jwtUtil.generateToken(usuario);

        String username =
                jwtUtil.extractUsername(token);

        assertEquals("admin", username);
    }

    @Test
    void validateToken_DebeRetornarTrue() {

        User usuario = new User(
                "admin",
                "1234",
                Collections.emptyList()
        );

        String token =
                jwtUtil.generateToken(usuario);

        assertTrue(
                jwtUtil.validateToken(
                        token,
                        usuario
                )
        );
    }
}
