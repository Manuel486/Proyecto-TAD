package com.microservice.proyect.client;

import com.microservice.proyect.dto.MensajeResponse;
import com.microservice.proyect.dto.UsuarioDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msvc-users")
public interface UsuarioClient {

    @Value("${msvc.usuarios.url}")
    String baseUrl = "";

    @GetMapping("/api/usuarios/{id}")
    ResponseEntity<MensajeResponse> getUsuarioById(@PathVariable("id") Integer id);

    @GetMapping("/api/usuarios")
    List<UsuarioDto> getAll();
}
