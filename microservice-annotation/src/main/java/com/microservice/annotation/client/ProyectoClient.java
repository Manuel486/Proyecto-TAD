package com.microservice.annotation.client;

import com.microservice.annotation.dto.MensajeResponse;
import com.microservice.annotation.dto.ProyectoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-project", url ="${msvc.proyectos.url}")
public interface ProyectoClient {

    @GetMapping("/api/proyectos/{id}")
    ResponseEntity<MensajeResponse> getProyectoById(@PathVariable("id") Integer id);

}