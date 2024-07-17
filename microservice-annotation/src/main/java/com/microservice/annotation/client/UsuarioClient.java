package com.microservice.annotation.client;

import com.microservice.annotation.dto.MensajeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msvc-users", url ="${msvc.usuarios.url}")
public interface UsuarioClient {

    @GetMapping("/api/usuarios/{id}")
    ResponseEntity<MensajeResponse> getUsuarioById(@PathVariable("id") Integer id);

}

