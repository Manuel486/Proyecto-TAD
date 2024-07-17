package com.microservice.auth.client;

import com.microservice.auth.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-users", url ="${msvc.usuarios.url}")
public interface UserFeignClient {


    @GetMapping("/api/usuarios/username/{username}")
    ResponseEntity<UserDto> obtenerUsuarioPorUsername (@PathVariable String username);
}
