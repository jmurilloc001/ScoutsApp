package com.jmurilloc.pfc.scouts.util;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UtilValidation {

    public static ResponseEntity<Object> validation(BindingResult result) {
        Map<String,String> errors = new HashMap<>();
        List<FieldError> fieldErrors = result.getFieldErrors();
        for (FieldError err : fieldErrors) {
            errors.put(err.getField(),"El campo " + err.getField() + " "  +err.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }
    public static BindingResult validateWithoutError(String messageOfError, BindingResult result){
        if (result.hasFieldErrors()){
            List<FieldError> errors = new ArrayList<>(result.getFieldErrors());
            errors.removeIf(error -> error.getDefaultMessage().equals(messageOfError));
            if (!errors.isEmpty()) {
                BindingResult newResult = new BeanPropertyBindingResult(result.getTarget(), result.getObjectName()); //Tengo que hacerlo así, ya que los errores que me devuelve getFieldErrors, es una lista que no se puede modificar
                for (FieldError error : errors) {
                    newResult.addError(error);
                }
                return newResult;
            }
            return null;
        }
        return null;
    }
}
