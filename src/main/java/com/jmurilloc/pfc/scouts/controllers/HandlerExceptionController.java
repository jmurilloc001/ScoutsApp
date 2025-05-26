package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.exceptions.AccesDeniedException;
import com.jmurilloc.pfc.scouts.exceptions.AffiliateCouldntCreateException;
import com.jmurilloc.pfc.scouts.exceptions.AffiliateInMeetingException;
import com.jmurilloc.pfc.scouts.exceptions.AffiliateNotFoundException;
import com.jmurilloc.pfc.scouts.exceptions.AffiliateWithUserException;
import com.jmurilloc.pfc.scouts.exceptions.BadDataException;
import com.jmurilloc.pfc.scouts.exceptions.CouncilDtoException;
import com.jmurilloc.pfc.scouts.exceptions.CouncilNotFoundException;
import com.jmurilloc.pfc.scouts.exceptions.EmailError;
import com.jmurilloc.pfc.scouts.exceptions.InsuficientStockException;
import com.jmurilloc.pfc.scouts.exceptions.MeetingNotFoundException;
import com.jmurilloc.pfc.scouts.exceptions.MettingOrAffiliateNotFoundException;
import com.jmurilloc.pfc.scouts.exceptions.NewDtoException;
import com.jmurilloc.pfc.scouts.exceptions.NewNotCreateException;
import com.jmurilloc.pfc.scouts.exceptions.NewNotFoundException;
import com.jmurilloc.pfc.scouts.exceptions.PostCouldntCreateException;
import com.jmurilloc.pfc.scouts.exceptions.PostNotFoundException;
import com.jmurilloc.pfc.scouts.exceptions.PostsDtoException;
import com.jmurilloc.pfc.scouts.exceptions.ProductCouldntCreateException;
import com.jmurilloc.pfc.scouts.exceptions.ProductNotDeleteException;
import com.jmurilloc.pfc.scouts.exceptions.ProductNotFoundException;
import com.jmurilloc.pfc.scouts.exceptions.RoleNotFoundException;
import com.jmurilloc.pfc.scouts.exceptions.TripDtoException;
import com.jmurilloc.pfc.scouts.exceptions.TripNotCreatedException;
import com.jmurilloc.pfc.scouts.exceptions.TripNotFoundException;
import com.jmurilloc.pfc.scouts.exceptions.UserNotFoundException;
import com.jmurilloc.pfc.scouts.exceptions.UserWithAffiliateException;
import com.jmurilloc.pfc.scouts.exceptions.UserWithoutRoleException;
import com.jmurilloc.pfc.scouts.models.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.Date;

@RestControllerAdvice
public class HandlerExceptionController
{
    
    @ExceptionHandler( {
            ProductNotFoundException.class,
            AffiliateNotFoundException.class,
            MeetingNotFoundException.class,
            MettingOrAffiliateNotFoundException.class,
            UserNotFoundException.class,
            RoleNotFoundException.class,
            CouncilNotFoundException.class,
            PostNotFoundException.class,
            NewNotFoundException.class,
            TripNotFoundException.class } )
    public ResponseEntity<Error> notFoundEx( Exception e )
    {
        Error error = new Error();
        error.setDate( new Date() );
        error.setErrorSpecification( "Api rest no encontrado" );
        error.setMessage( e.getMessage() );
        error.setStatus( HttpStatus.NOT_FOUND.value() );
        return ResponseEntity.status( HttpStatus.NOT_FOUND.value() ).body( error );
    }
    
    
    @ExceptionHandler( {
            ProductCouldntCreateException.class,
            CouncilDtoException.class,
            PostsDtoException.class,
            AffiliateCouldntCreateException.class,
            NewDtoException.class,
            NewNotCreateException.class,
            TripDtoException.class,
            TripNotCreatedException.class } )
    public ResponseEntity<Error> createError( Exception e )
    {
        Error error = new Error();
        error.setDate( new Date() );
        error.setErrorSpecification( "No se ha podido crear" );
        error.setMessage( e.getMessage() );
        error.setStatus( HttpStatus.BAD_REQUEST.value() );
        return ResponseEntity.status( HttpStatus.BAD_REQUEST.value() ).body( error );
    }
    
    
    @ExceptionHandler( { AffiliateInMeetingException.class, UserWithAffiliateException.class, AffiliateWithUserException.class } )
    public ResponseEntity<Error> relationCreatedException( Exception e )
    {
        Error error = new Error();
        error.setDate( new Date() );
        error.setErrorSpecification( "Ya estan relacionados" );
        error.setMessage( e.getMessage() );
        error.setStatus( HttpStatus.BAD_REQUEST.value() );
        return ResponseEntity.status( HttpStatus.BAD_REQUEST.value() ).body( error );
    }
    
    
    @ExceptionHandler( { ProductNotDeleteException.class } )
    public ResponseEntity<Error> notDeleteException( Exception e )
    {
        Error error = new Error();
        error.setDate( new Date() );
        error.setErrorSpecification( "No se ha podido borrar" );
        error.setMessage( e.getMessage() );
        error.setStatus( HttpStatus.BAD_REQUEST.value() );
        return ResponseEntity.status( HttpStatus.BAD_REQUEST.value() ).body( error );
    }
    
    
    @ExceptionHandler( { BadDataException.class, PostCouldntCreateException.class } )
    public ResponseEntity<Error> badDataException( Exception e )
    {
        Error error = new Error();
        error.setDate( new Date() );
        error.setErrorSpecification( "Datos mal introducidos" );
        error.setMessage( e.getMessage() );
        error.setStatus( HttpStatus.BAD_REQUEST.value() );
        return ResponseEntity.status( HttpStatus.BAD_REQUEST.value() ).body( error );
    }
    
    
    @ExceptionHandler( { UserWithoutRoleException.class } )
    public ResponseEntity<Error> userNotHaveRole( Exception e )
    {
        Error error = new Error();
        error.setDate( new Date() );
        error.setErrorSpecification( "El usuario no contiene el rol" );
        error.setMessage( e.getMessage() );
        error.setStatus( HttpStatus.BAD_REQUEST.value() );
        return ResponseEntity.status( HttpStatus.BAD_REQUEST.value() ).body( error );
    }
    
    
    @ExceptionHandler( { AccesDeniedException.class } )
    public ResponseEntity<Error> accesDenied( Exception e )
    {
        Error error = new Error();
        error.setDate( new Date() );
        error.setErrorSpecification( "No tienes permisos para hacer esta operación" );
        error.setMessage( e.getMessage() );
        error.setStatus( HttpStatus.UNAUTHORIZED.value() );
        return ResponseEntity.status( HttpStatus.UNAUTHORIZED.value() ).body( error );
    }
    
    
    @ExceptionHandler( MaxUploadSizeExceededException.class )
    public ResponseEntity<String> handleMaxSizeException( MaxUploadSizeExceededException exc )
    {
        return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( "El tamaño máximo de archivo permitido ha sido excedido." );
    }
    
    
    @ExceptionHandler( MissingServletRequestParameterException.class )
    public ResponseEntity<String> handlerParametrerException( MissingServletRequestParameterException e )
    {
        return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( "No se ha podido crear el objeto. " + e.getMessage() );
    }
    
    
    @ExceptionHandler( EmailError.class )
    public ResponseEntity<String> handlerMailException( MailException e )
    {
        return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( "No se ha podido enviar el correo. " + e.getMessage() );
    }
    
    
    @ExceptionHandler( InsuficientStockException.class )
    public ResponseEntity<String> handlerInsuficientStockException( MailException e )
    {
        return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( "No hay stock suficiente. Stock actual: " + e.getMessage() );
    }
}
