package com.microservice.proyect.persistence;

import com.microservice.proyect.entities.Proyecto;
import org.springframework.data.repository.CrudRepository;

public interface ProyectoRepository extends CrudRepository<Proyecto, Integer> {
}
