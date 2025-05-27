package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.Trip;
import com.jmurilloc.pfc.scouts.entities.dto.TripDto;
import com.jmurilloc.pfc.scouts.repositories.TripRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
class TripServiceImplTest
{
    
    @Mock
    private TripRepository tripRepository;
    
    @InjectMocks
    private TripServiceImpl tripService;
    
    
    @Test
    void testGetTripById_Success()
    {
        Long tripId = 1L;
        Trip trip = new Trip();
        trip.setId( tripId );
        
        when( tripRepository.findById( tripId ) ).thenReturn( Optional.of( trip ) );
        
        Optional<TripDto> result = tripService.getTripById( tripId );
        
        assertTrue( result.isPresent() );
        assertEquals( tripId, result.get().getId() );
        verify( tripRepository, times( 1 ) ).findById( tripId );
    }
    
    
    @Test
    void testGetTripById_NotFound()
    {
        Long tripId = 1L;
        
        when( tripRepository.findById( tripId ) ).thenReturn( Optional.empty() );
        
        Optional<TripDto> result = tripService.getTripById( tripId );
        
        assertFalse( result.isPresent() );
        verify( tripRepository, times( 1 ) ).findById( tripId );
    }
    
    
    @Test
    void testGetAllTrips_Success()
    {
        Trip trip = new Trip();
        trip.setId( 1L );
        
        when( tripRepository.findAll() ).thenReturn( List.of( trip ) );
        
        List<TripDto> result = tripService.getAllTrips();
        
        assertFalse( result.isEmpty() );
        assertEquals( 1, result.size() );
        verify( tripRepository, times( 1 ) ).findAll();
    }
    
    
    @Test
    void testGetAllTrips_Empty()
    {
        when( tripRepository.findAll() ).thenReturn( Collections.emptyList() );
        
        List<TripDto> result = tripService.getAllTrips();
        
        assertTrue( result.isEmpty() );
        verify( tripRepository, times( 1 ) ).findAll();
    }
    
    
    @Test
    void testGetAllTripsPaginated_Success()
    {
        Trip trip = new Trip();
        trip.setId( 1L );
        Page<Trip> tripPage = new PageImpl<>( List.of( trip ) );
        
        when( tripRepository.findAll( any( PageRequest.class ) ) ).thenReturn( tripPage );
        
        Page<TripDto> result = tripService.getAllTripsPaginated( 0, 10 );
        
        assertFalse( result.isEmpty() );
        assertEquals( 1, result.getContent().size() );
        verify( tripRepository, times( 1 ) ).findAll( any( PageRequest.class ) );
    }
    
    
    @Test
    void testCreateTrip_Success()
    {
        Trip trip = new Trip();
        trip.setId( 1L );
        TripDto tripDto = new TripDto();
        tripDto.setTitle( "Test Trip" );
        
        when( tripRepository.save( any( Trip.class ) ) ).thenReturn( trip );
        
        Optional<TripDto> result = tripService.createTrip( tripDto );
        
        assertTrue( result.isPresent() );
        assertEquals( 1L, result.get().getId() );
        verify( tripRepository, times( 1 ) ).save( any( Trip.class ) );
    }
    
    
    @Test
    void testCreateTrip_Failure()
    {
        TripDto tripDto = new TripDto();
        
        when( tripRepository.save( any( Trip.class ) ) ).thenThrow( new RuntimeException( "Error" ) );
        
        Optional<TripDto> result = tripService.createTrip( tripDto );
        
        assertFalse( result.isPresent() );
        verify( tripRepository, times( 1 ) ).save( any( Trip.class ) );
    }
}