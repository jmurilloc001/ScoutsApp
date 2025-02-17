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
    private void setService(UserService service) {
        this.service = service;
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (service == null){ //Esto es porque intenta hacer una segunda validación, y no deberia, entonces te da fallo, porque el service es null
            //Pero como ya ha pasado por la primera validación, pues mandamos true, ya que si se ha validado
            return true;
        }
        return !(service.existsByUsername(username));
    }
}
