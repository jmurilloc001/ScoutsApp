package com.jmurilloc.pfc.scouts.controllers;


import com.jmurilloc.pfc.scouts.exceptions.*;
import com.jmurilloc.pfc.scouts.models.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.Date;

@RestControllerAdvice
public class HandlerExceptionController {

    @ExceptionHandler({ProductNotFoundException.class, AffiliateNotFoundException.class, MeetingNotFoundException.class, MettingOrAffiliateNotFoundException.class, UserNotFoundException.class,RoleNotFoundException.class, CouncilNotFoundException.class, PostNotFoundException.class})
    public ResponseEntity<Error> notFoundEx(Exception e){
        Error error = new Error();
        error.setDate(new Date());
        error.setErrorSpecification("Api rest no encontrado");
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
    }
    @ExceptionHandler({ProductCouldntCreateException.class,CouncilDtoException.class})
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
    @ExceptionHandler({ProductNotDeleteException.class})
    public ResponseEntity<Error> notDeleteException(Exception e){
        Error error = new Error();
        error.setDate(new Date());
        error.setErrorSpecification("No se ha podido borrar");
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(error);
    }
    @ExceptionHandler({BadDataException.class, PostNotCouldntCreateException.class})
    public ResponseEntity<Error> badDataException(Exception e){
        Error error = new Error();
        error.setDate(new Date());
        error.setErrorSpecification("Datos mal introducidos");
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(error);
    }
    @ExceptionHandler({UserWithoutRoleException.class})
    public ResponseEntity<Error> userNotHaveRole(Exception e){
        Error error = new Error();
        error.setDate(new Date());
        error.setErrorSpecification("El usuario no contiene el rol");
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(error);
    }
    @ExceptionHandler({AccesDeniedException.class})
    public ResponseEntity<Error> accesDenied(Exception e){
        Error error = new Error();
        error.setDate(new Date());
        error.setErrorSpecification("No tienes permisos para hacer esta operación");
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(error);
    }
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El tamaño máximo de archivo permitido ha sido excedido.");
    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handlerParametrerException(MissingServletRequestParameterException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se ha podido crear el objeto. " + e.getMessage());
    }
}
