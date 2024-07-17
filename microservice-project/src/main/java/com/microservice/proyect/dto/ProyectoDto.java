package com.microservice.proyect.dto;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class ProyectoDto {
    private Integer id;
    private String titulo;
    private String descripcion;
    private Integer ciclo;
    private Integer usuarioIdDuenio;
}
