package com.microservice.user.validation;

import com.microservice.user.service.UsuarioService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExistByUsernameValidation implements ConstraintValidator<ExistByUsername, String> {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (usuarioService == null){
            return true;
        }
        return !usuarioService.existsByUsername(username);
    }
}
