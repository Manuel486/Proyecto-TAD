package com.microservice.annotation.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.annotation.client.ProyectoClient;
import com.microservice.annotation.client.UsuarioClient;
import com.microservice.annotation.dto.MensajeResponse;
import com.microservice.annotation.dto.ProyectoDto;
import com.microservice.annotation.dto.UsuarioDto;
import com.microservice.annotation.entities.Anotacion;
import com.microservice.annotation.persistence.AnotacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnotacionService {

    @Autowired
    private AnotacionRepository anotacionRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private ProyectoClient proyectoClient;

    private ObjectMapper objectMapper = new ObjectMapper();

    public List<Anotacion> findAll() {
        return anotacionRepository.findAll();
    }

    public Anotacion findById(Integer id) {
        return anotacionRepository.findById(id).orElse(null);
    }

    public List<Anotacion> findByProyectoId(Integer proyectoId) {
        return anotacionRepository.findByProyectoId(proyectoId);
    }

    public List<Anotacion> findByUsuarioId(Integer usuarioId) {
        return anotacionRepository.findByUsuarioId(usuarioId);
    }

    public List<Anotacion> findByUsuarioIdAndProyectoId(Integer usuarioId, Integer proyectoId) {
        return anotacionRepository.findByUsuarioIdAndProyectoId(usuarioId, proyectoId);
    }

    public Anotacion save(Anotacion anotacion) {

        ResponseEntity<MensajeResponse> usuarioResponse = usuarioClient.getUsuarioById(anotacion.getUsuarioId());
        ResponseEntity<MensajeResponse> proyectoResponse = proyectoClient.getProyectoById(anotacion.getProyectoId());

        // Deserializar el objeto dentro de MensajeResponse
        UsuarioDto usuario = objectMapper.convertValue(usuarioResponse.getBody().getObject(), UsuarioDto.class);
        ProyectoDto proyecto = objectMapper.convertValue(proyectoResponse.getBody().getObject(), ProyectoDto.class);


        if (usuario.getId() != null && proyecto.getId() != null) {
            System.out.println(proyecto);
            System.out.println(usuario);
            if (!proyecto.getUsuarioIds().contains(anotacion.getUsuarioId())) {
                throw new IllegalArgumentException("El usuario no está asignado a este proyecto");
            }
            return anotacionRepository.save(anotacion);
        } else {
            throw new IllegalArgumentException("Usuario o Proyecto no encontrado");
        }
    }

    public void deleteById(Integer id) {
        anotacionRepository.deleteById(id);
    }
}
