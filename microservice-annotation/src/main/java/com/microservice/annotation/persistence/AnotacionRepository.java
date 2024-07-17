package com.microservice.annotation.persistence;

import com.microservice.annotation.entities.Anotacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnotacionRepository extends JpaRepository<Anotacion, Integer> {
    List<Anotacion> findByProyectoId(Integer proyectoId);
    List<Anotacion> findByUsuarioId(Integer usuarioId);
    List<Anotacion> findByUsuarioIdAndProyectoId(Integer usuarioId, Integer proyectoId);
}
