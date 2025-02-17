package com.jmurilloc.pfc.scouts.vaidation;

import com.jmurilloc.pfc.scouts.services.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExistsByUsernameValidation implements ConstraintValidator<ExistsByUsername,String> {

    private UserService service;

    @Autowired
    public ExistsByUsernameValidation(UserService service){
        this.service = service;
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return !(service.existsByUsername(username));
    }
}
