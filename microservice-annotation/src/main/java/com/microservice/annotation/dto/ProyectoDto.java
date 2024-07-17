package com.microservice.annotation.dto;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class ProyectoDto {
    private Integer id;
    private String titulo;
    private String descripcion;
    private Date fechaCreacion;
    private Integer ciclo;
    private Integer usuarioIdDuenio;
    private List<Integer> usuarioIds;
}