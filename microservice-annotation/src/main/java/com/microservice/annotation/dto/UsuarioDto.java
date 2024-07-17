package com.microservice.annotation.dto;

import lombok.Data;
import java.util.Set;

@Data
public class UsuarioDto {
    private Integer id;
    private String nombre;
    private String codigo;
    private String email;
    private String username;
    private String password;
    private String fotoPerfil;
}
