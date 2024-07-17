package com.microservice.proyect.controller;

import com.microservice.proyect.dto.MensajeResponse;
import com.microservice.proyect.dto.ProyectoDto;
import com.microservice.proyect.dto.UsuarioDto;
import com.microservice.proyect.entities.Proyecto;
import com.microservice.proyect.exception.UsuarioAlreadyAddedException;
import com.microservice.proyect.exception.UsuarioNotFoundException;
import com.microservice.proyect.services.ProyectoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoServiceImpl proyectoService;

    @GetMapping
    public List<Proyecto> getAll() {
        return proyectoService.listAll();
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ProyectoDto proyectoDto) {
        Proyecto proyectoSave = null;

        try {
            proyectoSave = proyectoService.save(proyectoDto);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Guardado correctamente")
                    .object(proyectoSave)
                    .build()
            , HttpStatus.CREATED);

        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(exDt.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody ProyectoDto proyectoDto) {
        Proyecto proyectoUpdate = null;

        try {
            if(proyectoService.existsById(id)) {
                proyectoDto.setId(id);
                proyectoUpdate = proyectoService.save(proyectoDto);
                return new ResponseEntity<>(MensajeResponse.builder()
                            .mensaje("Actualizado correctamente")
                            .object(ProyectoDto.builder()
                                    .id(proyectoUpdate.getId())
                                    .titulo(proyectoUpdate.getTitulo())
                                    .descripcion(proyectoUpdate.getDescripcion())
                                    .ciclo(proyectoUpdate.getCiclo())
                                    .usuarioIdDuenio(proyectoUpdate.getUsuarioIdDuenio())
                                    .build())
                            .build()
                        , HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .mensaje("El registro que intenta actualizar no se encuentra en la base de datos.")
                                .object(null)
                                .build()
                        , HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(exDt.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Proyecto proyectoDelete = proyectoService.findById(id);

            if (proyectoDelete == null){
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .mensaje("El registro que intenta eliminar, no existe!!")
                                .object(null)
                                .build()
                        , HttpStatus.NOT_FOUND
                );
            }

            proyectoService.delete(proyectoDelete);
            return new ResponseEntity<>(proyectoDelete,HttpStatus.NO_CONTENT);
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(exDt.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id) {

        Proyecto proyecto = proyectoService.findById(id);

        if ( proyecto == null) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("El registro que intenta buscar, no existe!!")
                            .object(null)
                            .build()
                    , HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("")
                        .object(proyecto)
                        .build()
                , HttpStatus.OK);

    }


    @PostMapping("/{proyectoId}/usuarios/{usuarioId}")
    public ResponseEntity<?> addUsuarioToProyecto(@PathVariable Integer proyectoId, @PathVariable Integer usuarioId) {
        try {
            Proyecto proyecto = proyectoService.addUsuarioToProyecto(proyectoId, usuarioId);

            if (proyecto == null) {
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .mensaje("Proyecto no encontrado con ID: " + proyectoId)
                                .object(null)
                                .build(),
                        HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("Usuario añadido al proyecto exitosamente")
                            .object(proyecto)
                            .build(),
                    HttpStatus.OK);
        } catch (UsuarioNotFoundException ex) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(ex.getMessage())
                            .object(null)
                            .build(),
                    HttpStatus.NOT_FOUND);
        } catch (UsuarioAlreadyAddedException ex) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(ex.getMessage())
                            .object(null)
                            .build(),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("Ocurrió un error al procesar la solicitud.")
                            .object(null)
                            .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{proyectoId}/usuarios/{usuarioId}")
    public ResponseEntity<?> removeUsuarioFromProyecto(@PathVariable Integer proyectoId, @PathVariable Integer usuarioId) {
        try {
            Proyecto proyecto = proyectoService.removeUsuarioFromProyecto(proyectoId, usuarioId);

            if (proyecto == null) {
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .mensaje("Proyecto o Usuario no encontrado.")
                                .object(null)
                                .build(),
                        HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("Usuario removido del proyecto exitosamente")
                            .object(proyecto)
                            .build(),
                    HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("Ocurrió un error al procesar la solicitud.")
                            .object(null)
                            .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
