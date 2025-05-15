package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.exceptions.AccesDeniedException;
import com.jmurilloc.pfc.scouts.exceptions.AffiliateInMeetingException;
import com.jmurilloc.pfc.scouts.exceptions.BadDataException;
import com.jmurilloc.pfc.scouts.exceptions.CouncilDtoException;
import com.jmurilloc.pfc.scouts.exceptions.CouncilNotFoundException;
import com.jmurilloc.pfc.scouts.exceptions.ProductNotDeleteException;
import com.jmurilloc.pfc.scouts.exceptions.UserWithoutRoleException;
import com.jmurilloc.pfc.scouts.models.Error;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith( MockitoExtension.class )
class HandlerExceptionControllerTest
{
    
    @InjectMocks
    private HandlerExceptionController handler;
    
    
    @Test
    void notFoundExTest()
    {
        Exception exception = new CouncilNotFoundException( "Consejo no encontrado" );
        
        ResponseEntity<com.jmurilloc.pfc.scouts.models.Error> response = handler.notFoundEx( exception );
        
        assertNotNull( response );
        assertEquals( HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue() );
        assertEquals( "Consejo no encontrado", response.getBody().getMessage() );
    }
    
    
    @Test
    void createErrorTest()
    {
        Exception exception = new CouncilDtoException( "Error al crear el DTO del consejo" );
        
        ResponseEntity<com.jmurilloc.pfc.scouts.models.Error> response = handler.createError( exception );
        
        assertNotNull( response );
        assertEquals( HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue() );
        assertEquals( "Error al crear el DTO del consejo", response.getBody().getMessage() );
    }
    
    
    @Test
    void relationCreatedExceptionTest()
    {
        Exception exception = new AffiliateInMeetingException( "Afiliado ya está en la reunión" );
        
        ResponseEntity<com.jmurilloc.pfc.scouts.models.Error> response = handler.relationCreatedException( exception );
        
        assertNotNull( response );
        assertEquals( HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue() );
        assertEquals( "Afiliado ya está en la reunión", response.getBody().getMessage() );
    }
    
    
    @Test
    void notDeleteExceptionTest()
    {
        Exception exception = new ProductNotDeleteException( "No se pudo borrar el producto" );
        
        ResponseEntity<com.jmurilloc.pfc.scouts.models.Error> response = handler.notDeleteException( exception );
        
        assertNotNull( response );
        assertEquals( HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue() );
        assertEquals( "No se pudo borrar el producto", response.getBody().getMessage() );
    }
    
    
    @Test
    void badDataExceptionTest()
    {
        Exception exception = new BadDataException( "Datos incorrectos" );
        
        ResponseEntity<com.jmurilloc.pfc.scouts.models.Error> response = handler.badDataException( exception );
        
        assertNotNull( response );
        assertEquals( HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue() );
        assertEquals( "Datos incorrectos", response.getBody().getMessage() );
    }
    
    
    @Test
    void userNotHaveRoleTest()
    {
        Exception exception = new UserWithoutRoleException( "El usuario no tiene rol asignado" );
        
        ResponseEntity<com.jmurilloc.pfc.scouts.models.Error> response = handler.userNotHaveRole( exception );
        
        assertNotNull( response );
        assertEquals( HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue() );
        assertEquals( "El usuario no tiene rol asignado", response.getBody().getMessage() );
    }
    
    
    @Test
    void accesDeniedTest()
    {
        Exception exception = new AccesDeniedException( "Acceso denegado" );
        
        ResponseEntity<Error> response = handler.accesDenied( exception );
        
        assertNotNull( response );
        assertEquals( HttpStatus.UNAUTHORIZED.value(), response.getStatusCodeValue() );
    }
    
    
    @Test
    void handleMaxSizeExceptionTest()
    {
        MaxUploadSizeExceededException exception = new MaxUploadSizeExceededException( 1024 );
        
        ResponseEntity<String> response = handler.handleMaxSizeException( exception );
        
        assertNotNull( response );
        assertEquals( HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue() );
        assertEquals( "El tamaño máximo de archivo permitido ha sido excedido.", response.getBody() );
    }
    
    
    @Test
    void handlerParametrerExceptionTest()
    {
        MissingServletRequestParameterException exception = new MissingServletRequestParameterException( "param", "String" );
        
        ResponseEntity<String> response = handler.handlerParametrerException( exception );
        
        assertNotNull( response );
        assertEquals( HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue() );
        assertEquals( "No se ha podido crear el objeto. Required request parameter 'param' for method parameter type String is not present", response.getBody() );
    }
}