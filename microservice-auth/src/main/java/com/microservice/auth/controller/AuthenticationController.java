package com.microservice.auth.controller;

import com.microservice.auth.controller.dto.AuthCreateUserRequest;
import com.microservice.auth.controller.dto.AuthLoginRequest;
import com.microservice.auth.controller.dto.AuthResponse;
import com.microservice.auth.controller.dto.TokenDto;
import com.microservice.auth.service.UserDetailServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

//    @PostMapping("/sign-up")
//    public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthCreateUserRequest userRequest){
//        return new ResponseEntity<>(this.userDetailService.createUser(userRequest), HttpStatus.CREATED);
//    }

    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest){
        return new ResponseEntity<>(this.userDetailService.loginUser(userRequest), HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<TokenDto> validate(@RequestParam String token){
        TokenDto tokenDto = userDetailService.validate(token);
        if(tokenDto == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(tokenDto);
    }

}