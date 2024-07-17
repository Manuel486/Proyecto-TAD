package com.microservice.user.dto;

import com.microservice.user.validation.ExistByUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Data
@ToString
@Builder
public class UsuarioDto implements Serializable {

    private Integer id;

    @Size(min = 2, max = 25)
    @NotEmpty(message = "Nombre requerido!")
    private String nombre;

    @Size(min = 8, max = 8, message = "El código debe tener exactamente 8 caracteres.")
    @NotEmpty(message = "Código requerido!")
    private String codigo;

    @NotEmpty(message = "Correo requerido!")
    @Email
    private String email;

    @ExistByUsername
    @Size(min = 2, max = 25)
    @NotEmpty(message = "Nombre de usuario requerido!")
    private String username;

    @NotEmpty(message = "Contraseña requerido!")
    private String password;
    @Size(min = 2, max = 25)

    @NotEmpty(message = "Foto de perfil requerido!")
    private String fotoPerfil;
}