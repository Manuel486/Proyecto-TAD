package com.microservice.user.controller;

import com.microservice.user.dto.MensajeResponse;
import com.microservice.user.dto.UsuarioDto;
import com.microservice.user.entities.Usuario;
import com.microservice.user.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<UsuarioDto> getAll() {
        return usuarioService.listAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid UsuarioDto usuarioDto, BindingResult result) {

        if(result.hasFieldErrors()){
            return validation(result);
        }

        Usuario usuarioSave = null;
        try {
            usuarioSave =  usuarioService.save(usuarioDto);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Guardado correctamente")
                    .object(UsuarioDto.builder()
                            .id(usuarioSave.getId())
                            .nombre(usuarioSave.getNombre())
                            .codigo(usuarioSave.getCodigo())
                            .email(usuarioSave.getEmail())
                            .username(usuarioSave.getUsername())
                            .password(usuarioSave.getPassword())
                            .fotoPerfil(usuarioSave.getFotoPerfil())
                            .build()
                    ).build()
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuarioById(@PathVariable Integer id) {
        UsuarioDto usuarioDto = usuarioService.findById(id);

        if (usuarioDto == null){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("El registro que intenta buscar, no existe!!")
                            .object(null)
                            .build()
                    , HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
          MensajeResponse.builder()
                  .mensaje("")
                  .object(usuarioDto)
                  .build()
                , HttpStatus.OK
        );
    }

    @GetMapping("username/{username}")
    public ResponseEntity<Usuario> obtenerUsuarioPorUsername (@PathVariable String username) {
        Usuario usuario = usuarioService.getUsuarioByUsername(username);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid UsuarioDto usuarioDto, BindingResult result) {

        if(result.hasFieldErrors()){
            return validation(result);
        }

        Usuario usuarioUpdate = null;

        try {
            if(usuarioService.existsById(id)) {
                usuarioDto.setId(id);
                usuarioUpdate = usuarioService.save(usuarioDto);
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Actualizado correctamente")
                        .object(UsuarioDto.builder()
                            .id(usuarioUpdate.getId())
                            .nombre(usuarioUpdate.getNombre())
                            .codigo(usuarioUpdate.getCodigo())
                            .email(usuarioUpdate.getEmail())
                            .username(usuarioUpdate.getUsername())
                            .password(usuarioUpdate.getPassword())
                            .fotoPerfil(usuarioUpdate.getFotoPerfil())
                            .build())
                        .build()
                        , HttpStatus.CREATED
                );
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
            UsuarioDto usuarioDelete = usuarioService.findById(id);

            if (usuarioDelete == null){
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .mensaje("El registro que intenta eliminar, no existe!!")
                                .object(null)
                                .build()
                        , HttpStatus.NOT_FOUND
                );
            }

            usuarioService.delete(id);

            return new ResponseEntity<>(usuarioDelete, HttpStatus.NO_CONTENT);

        } catch (DataAccessException exDt){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(exDt.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED
            );
        }

    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String,String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(),err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}

