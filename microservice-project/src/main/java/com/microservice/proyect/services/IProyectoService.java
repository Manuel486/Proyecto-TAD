package com.microservice.proyect.services;

import com.microservice.proyect.dto.ProyectoDto;
import com.microservice.proyect.entities.Proyecto;

import java.util.List;

public interface IProyectoService {
    List<Proyecto> listAll();
    Proyecto save(ProyectoDto proyectoDto);
    Proyecto findById(Integer id);
    void delete(Proyecto proyecto);
    boolean existsById(Integer id);
    Proyecto addUsuarioToProyecto(Integer proyectoId, Integer usuarioId);
    Proyecto removeUsuarioFromProyecto(Integer proyectoId, Integer usuarioId);
}
