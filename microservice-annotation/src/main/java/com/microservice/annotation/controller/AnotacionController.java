package com.microservice.annotation.controller;

import com.microservice.annotation.entities.Anotacion;
import com.microservice.annotation.services.AnotacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anotaciones")
public class AnotacionController {

    @Autowired
    private AnotacionService anotacionService;

    @GetMapping
    public List<Anotacion> getAll() {
        return anotacionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anotacion> getById(@PathVariable Integer id) {
        Anotacion anotacion = anotacionService.findById(id);
        if (anotacion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(anotacion);
    }

    @GetMapping("/proyecto/{proyectoId}")
    public List<Anotacion> getByProyectoId(@PathVariable Integer proyectoId) {
        return anotacionService.findByProyectoId(proyectoId);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Anotacion> getByUsuarioId(@PathVariable Integer usuarioId) {
        return anotacionService.findByUsuarioId(usuarioId);
    }

    @GetMapping("/usuario/{usuarioId}/proyecto/{proyectoId}")
    public List<Anotacion> getByUsuarioIdAndProyectoId(@PathVariable Integer usuarioId, @PathVariable Integer proyectoId) {
        return anotacionService.findByUsuarioIdAndProyectoId(usuarioId, proyectoId);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Anotacion anotacion) {
        try {
            Anotacion savedAnotacion = anotacionService.save(anotacion);
            return ResponseEntity.ok(savedAnotacion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Anotacion> update(@PathVariable Integer id, @RequestBody Anotacion anotacion) {
        if (anotacionService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        anotacion.setId(id);
        return ResponseEntity.ok(anotacionService.save(anotacion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (anotacionService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        anotacionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}