package com.jmurilloc.pfc.scouts.controllers;


import com.jmurilloc.pfc.scouts.exceptions.ProductNotFoundException;
import com.jmurilloc.pfc.scouts.models.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class HandlerExceptionController {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Error> notFoundEx(ProductNotFoundException e){
        Error error = new Error();
        error.setDate(new Date());
        error.setErrorSpecification("Api rest no encontrado");
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
    }
}
