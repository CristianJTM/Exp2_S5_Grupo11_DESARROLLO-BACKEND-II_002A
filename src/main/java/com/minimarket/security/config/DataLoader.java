package com.minimarket.security.config;

import com.minimarket.entity.Rol;
import com.minimarket.entity.Usuario;
import com.minimarket.repository.RolRepository;
import com.minimarket.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(
            UsuarioRepository usuarioRepository,
            RolRepository rolRepository,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        if (rolRepository.count() == 0) {

            Rol gerente = new Rol();
            gerente.setNombre("GERENTE");

            Rol empleado = new Rol();
            empleado.setNombre("EMPLEADO");

            rolRepository.save(gerente);
            rolRepository.save(empleado);

            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRoles(Set.of(gerente));

            Usuario vendedor = new Usuario();
            vendedor.setUsername("empleado");
            vendedor.setPassword(passwordEncoder.encode("empleado123"));
            vendedor.setRoles(Set.of(empleado));

            usuarioRepository.save(admin);
            usuarioRepository.save(vendedor);

            System.out.println("Usuarios y roles creados");
        }
    }
}
