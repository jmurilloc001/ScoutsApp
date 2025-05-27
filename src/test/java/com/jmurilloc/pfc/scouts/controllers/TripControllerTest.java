package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.entities.dto.TripDto;
import com.jmurilloc.pfc.scouts.exceptions.TripNotFoundException;
import com.jmurilloc.pfc.scouts.services.interfaces.TripService;
import com.jmurilloc.pfc.scouts.util.MessageError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
class TripControllerTest
{
    
    @Mock
    private TripService tripService;
    
    @InjectMocks
    private TripController tripController;
    
    
    @Test
    void testGetTripById_Success()
    {
        Long tripId = 1L;
        TripDto tripDto = new TripDto();
        tripDto.setId( tripId );
        
        when( tripService.getTripById( tripId ) ).thenReturn( Optional.of( tripDto ) );
        
        ResponseEntity<TripDto> response = tripController.getTripById( tripId );
        
        assertNotNull( response );
        assertEquals( 200, response.getStatusCode().value() );
        assertEquals( tripId, response.getBody().getId() );
        verify( tripService, times( 1 ) ).getTripById( tripId );
    }
    
    
    @Test
    void testGetTripById_NotFound()
    {
        Long tripId = 1L;
        
        when( tripService.getTripById( tripId ) ).thenReturn( Optional.empty() );
        
        TripNotFoundException exception = assertThrows( TripNotFoundException.class, () -> tripController.getTripById( tripId ) );
        
        assertEquals( MessageError.TRIP_NOT_FOUND.getValue(), exception.getMessage() );
        verify( tripService, times( 1 ) ).getTripById( tripId );
    }
    
    
    @Test
    void testGetAllTripsPaginated_Success()
    {
        int page = 0;
        int size = 10;
        Page<TripDto> tripPage = new PageImpl<>( Collections.singletonList( new TripDto() ) );
        
        when( tripService.getAllTripsPaginated( page, size ) ).thenReturn( tripPage );
        
        ResponseEntity<Page<TripDto>> response = tripController.getAllTripsPaginated( page, size );
        
        assertNotNull( response );
        assertEquals( 200, response.getStatusCode().value() );
        assertTrue( response.getBody().hasContent() );
        verify( tripService, times( 1 ) ).getAllTripsPaginated( page, size );
    }
    
    
    @Test
    void testGetAllTripsPaginated_NotFound()
    {
        int page = 0;
        int size = 10;
        Page<TripDto> emptyPage = new PageImpl<>( Collections.emptyList() );
        
        when( tripService.getAllTripsPaginated( page, size ) ).thenReturn( emptyPage );
        
        TripNotFoundException exception = assertThrows( TripNotFoundException.class, () -> tripController.getAllTripsPaginated( page, size ) );
        
        assertEquals( MessageError.TRIP_NOT_FOUND.getValue(), exception.getMessage() );
        verify( tripService, times( 1 ) ).getAllTripsPaginated( page, size );
    }
    
    
    @Test
    void testCreateTrip_Success()
    {
        TripDto tripDto = new TripDto();
        tripDto.setTitle( "Test Trip" );
        
        when( tripService.createTrip( tripDto ) ).thenReturn( Optional.of( tripDto ) );
        
        ResponseEntity<TripDto> response = tripController.createTrip( tripDto );
        
        assertNotNull( response );
        assertEquals( 201, response.getStatusCode().value() );
        assertEquals( "Test Trip", response.getBody().getTitle() );
        verify( tripService, times( 1 ) ).createTrip( tripDto );
    }
    
    
    @Test
    void testCreateTrip_Failure()
    {
        TripDto tripDto = new TripDto();
        
        when( tripService.createTrip( tripDto ) ).thenReturn( Optional.empty() );
        
        TripNotFoundException exception = assertThrows( TripNotFoundException.class, () -> tripController.createTrip( tripDto ) );
        
        assertEquals( MessageError.TRIP_NOT_CREATED.getValue(), exception.getMessage() );
        verify( tripService, times( 1 ) ).createTrip( tripDto );
    }
}