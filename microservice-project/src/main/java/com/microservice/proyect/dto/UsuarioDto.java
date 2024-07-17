package com.microservice.proyect.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UsuarioDto {
    private Integer id;
    private String nombre;
    private String codigo;
    private String email;
    private String username;
    private String password;
    private String fotoPerfil;
}
