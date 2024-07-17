package com.microservice.proyect.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.proyect.client.UsuarioClient;
import com.microservice.proyect.dto.MensajeResponse;
import com.microservice.proyect.dto.ProyectoDto;
import com.microservice.proyect.dto.UsuarioDto;
import com.microservice.proyect.entities.Proyecto;
import com.microservice.proyect.exception.UsuarioAlreadyAddedException;
import com.microservice.proyect.exception.UsuarioNotFoundException;
import com.microservice.proyect.persistence.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProyectoServiceImpl implements IProyectoService{

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Proyecto> listAll() {
        return (List<Proyecto>) proyectoRepository.findAll();
    }

    @Transactional
    @Override
    public Proyecto save(ProyectoDto proyectoDto) {
        LocalDateTime now = LocalDateTime.now();

        Proyecto proyecto = Proyecto.builder()
                .id(proyectoDto.getId())
                .titulo(proyectoDto.getTitulo())
                .descripcion(proyectoDto.getDescripcion())
                .fechaCreacion(now)
                .ciclo(proyectoDto.getCiclo())
                .usuarioIdDuenio(proyectoDto.getUsuarioIdDuenio())
                .usuarioIds(new ArrayList<>())
                .build();
        return proyectoRepository.save(proyecto);
    }

    @Override
    public Proyecto findById(Integer id) {
        return proyectoRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Proyecto proyecto) {
        proyectoRepository.delete(proyecto);
    }

    @Override
    public boolean existsById(Integer id) {
        return proyectoRepository.existsById(id);
    }

    public Proyecto addUsuarioToProyecto(Integer proyectoId, Integer usuarioId) {
        Proyecto proyecto = findById(proyectoId);
        if (proyecto == null) {
            return null;
        }

        ResponseEntity<MensajeResponse> responseEntity = usuarioClient.getUsuarioById(usuarioId);
        UsuarioDto usuario = objectMapper.convertValue(responseEntity.getBody().getObject(), UsuarioDto.class);
        if (usuario == null) {
            throw new UsuarioNotFoundException("Usuario no encontrado con ID: " + usuarioId);
        }

        if (proyecto.getUsuarioIds().contains(usuarioId)) {
            throw new UsuarioAlreadyAddedException("El usuario con ID " + usuarioId + " ya está añadido al proyecto con ID " + proyectoId);
        }

        proyecto.getUsuarioIds().add(usuarioId);
        return proyectoRepository.save(proyecto);
    }

    public Proyecto removeUsuarioFromProyecto(Integer proyectoId, Integer usuarioId) {
        Proyecto proyecto = findById(proyectoId);
        if (proyecto == null) {
            return null;
        }

        ResponseEntity<MensajeResponse> responseEntity = usuarioClient.getUsuarioById(usuarioId);
        UsuarioDto usuario = objectMapper.convertValue(responseEntity.getBody().getObject(), UsuarioDto.class);
        if (usuario == null) {
            return null;
        }

        proyecto.getUsuarioIds().remove(usuarioId);
        return proyectoRepository.save(proyecto);
    }
}
