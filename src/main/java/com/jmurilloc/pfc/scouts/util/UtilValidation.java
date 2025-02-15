package com.jmurilloc.pfc.scouts.util;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

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
}
