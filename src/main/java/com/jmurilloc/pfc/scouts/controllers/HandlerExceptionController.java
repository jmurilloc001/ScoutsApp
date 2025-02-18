package com.jmurilloc.pfc.scouts.controllers;


import com.jmurilloc.pfc.scouts.exceptions.*;
import com.jmurilloc.pfc.scouts.models.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class HandlerExceptionController {

    @ExceptionHandler({ProductNotFoundException.class, AffiliateNotFoundException.class, MeetingNotFound.class, MettingOrAffiliateNotFoundException.class, UserNotFoundException.class,RoleNotFoundException.class})
    public ResponseEntity<Error> notFoundEx(Exception e){
        Error error = new Error();
        error.setDate(new Date());
        error.setErrorSpecification("Api rest no encontrado");
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
    }
    @ExceptionHandler({ProductCouldntCreateException.class})
    public ResponseEntity<Error> createError(Exception e){
        Error error = new Error();
        error.setDate(new Date());
        error.setErrorSpecification("No se ha podido crear");
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(error);
    }
    @ExceptionHandler({AffiliateInMeetingException.class,UserWithAffiliateException.class,AffiliateWithUserException.class})
    public ResponseEntity<Error> relationCreatedException(Exception e){
        Error error = new Error();
        error.setDate(new Date());
        error.setErrorSpecification("Ya estan relacionados");
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(error);
    }

}
