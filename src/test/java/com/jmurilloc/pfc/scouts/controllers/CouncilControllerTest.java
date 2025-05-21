package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.entities.Council;
import com.jmurilloc.pfc.scouts.services.interfaces.CouncilService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
class CouncilControllerTest
{
    
    @Mock
    private CouncilService councilService;
    
    @InjectMocks
    private CouncilController councilController;
    
    
    @Test
    void findByIdTest()
    {
        Long councilId = 1L;
        Council council = new Council();
        when( councilService.findById( councilId ) ).thenReturn( council );
        
        ResponseEntity<Council> response = councilController.findById( councilId );
        
        assertNotNull( response );
        assertEquals( council, response.getBody() );
        verify( councilService ).findById( councilId );
    }
    
    
    @Test
    void getCouncilsByFechaTest()
    {
        Date fecha = new Date();
        List<Council> councils = List.of( new Council(), new Council() );
        when( councilService.findByInitialDate( fecha ) ).thenReturn( councils );
        
        ResponseEntity<Object> response = councilController.getCouncilsByFecha( fecha );
        
        assertNotNull( response );
        assertEquals( HttpStatus.OK, response.getStatusCode() );
        verify( councilService ).findByInitialDate( fecha );
    }
    
    
    @Test
    void createCouncilTest() throws IOException
    {
        Date fechaInicio = new Date();
        Date fechaFin = new Date();
        MultipartFile puntosConsejo = mock( MultipartFile.class );
        MultipartFile puntosConsejoAnterior = mock( MultipartFile.class );
        Council savedCouncil = new Council();
        
        when( puntosConsejo.getBytes() ).thenReturn( new byte[] { 1, 2, 3 } );
        when( puntosConsejoAnterior.getBytes() ).thenReturn( new byte[] { 4, 5, 6 } );
        when( councilService.save( any( Council.class ) ) ).thenReturn( savedCouncil );
        
        ResponseEntity<Object> response = councilController.createCouncil( fechaInicio, fechaFin, puntosConsejo, puntosConsejoAnterior );
        
        assertNotNull( response );
        assertEquals( HttpStatus.CREATED, response.getStatusCode() );
        verify( councilService ).save( any( Council.class ) );
    }
    
    
    @Test
    void deleteCouncilTest()
    {
        Long councilId = 1L;
        
        ResponseEntity<Object> response = councilController.deleteCouncil( councilId );
        
        assertNotNull( response );
        assertEquals( "Borrado con exito", response.getBody() );
        verify( councilService ).deleteById( councilId );
    }
}