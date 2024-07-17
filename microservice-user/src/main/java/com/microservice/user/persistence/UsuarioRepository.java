package com.microservice.user.persistence;

import com.microservice.user.entities.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario,Integer> {
    boolean existsByUsername(String username);
    Optional<Usuario> findUsuarioByUsername(String username);
}
