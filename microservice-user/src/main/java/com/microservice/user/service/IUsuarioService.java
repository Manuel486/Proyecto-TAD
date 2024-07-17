package com.microservice.user.service;

import com.microservice.user.dto.UsuarioDto;
import com.microservice.user.entities.Usuario;

import java.util.List;

public interface IUsuarioService {

    List<UsuarioDto> listAll();
    Usuario save(UsuarioDto usuarioDto);
    UsuarioDto findById(Integer id);
    void delete(Integer id);
    boolean existsById(Integer id);
    boolean existsByUsername(String username);
}
