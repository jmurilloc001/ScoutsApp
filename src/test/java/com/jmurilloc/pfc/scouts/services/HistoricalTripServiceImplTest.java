package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.HistoricalTrip;
import com.jmurilloc.pfc.scouts.entities.dto.HistoricalTripDto;
import com.jmurilloc.pfc.scouts.repositories.HistoricalTripRepository;
import com.jmurilloc.pfc.scouts.util.BuildEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
class HistoricalTripServiceImplTest
{
    
    @Mock
    private HistoricalTripRepository historicalTripRepository;
    
    @InjectMocks
    private HistoricalTripServiceImpl historicalTripService;
    
    
    @Test
    void testGetHistoricalTripById_Success()
    {
        Long id = 1L;
        HistoricalTrip historicalTrip = new HistoricalTrip();
        historicalTrip.setId( id );
        
        when( historicalTripRepository.findById( id ) ).thenReturn( Optional.of( historicalTrip ) );
        
        Optional<HistoricalTripDto> result = historicalTripService.getHistoricalTripById( id );
        
        assertTrue( result.isPresent() );
        assertEquals( id, result.get().getId() );
        verify( historicalTripRepository, times( 1 ) ).findById( id );
    }
    
    
    @Test
    void testGetHistoricalTripById_NotFound()
    {
        Long id = 1L;
        
        when( historicalTripRepository.findById( id ) ).thenReturn( Optional.empty() );
        
        Optional<HistoricalTripDto> result = historicalTripService.getHistoricalTripById( id );
        
        assertFalse( result.isPresent() );
        verify( historicalTripRepository, times( 1 ) ).findById( id );
    }
    
    
    @Test
    void testGetHistoricalTripByTripId_Success()
    {
        Long tripId = 1L;
        HistoricalTrip historicalTrip = new HistoricalTrip();
        historicalTrip.setTripId( tripId );
        
        when( historicalTripRepository.findByTripId( tripId ) ).thenReturn( Optional.of( historicalTrip ) );
        
        Optional<HistoricalTripDto> result = historicalTripService.getHistoricalTripByTripId( tripId );
        
        assertTrue( result.isPresent() );
        assertEquals( tripId, result.get().getTripId() );
        verify( historicalTripRepository, times( 1 ) ).findByTripId( tripId );
    }
    
    
    @Test
    void testGetHistoricalTripByTripId_NotFound()
    {
        Long tripId = 1L;
        
        when( historicalTripRepository.findByTripId( tripId ) ).thenReturn( Optional.empty() );
        
        Optional<HistoricalTripDto> result = historicalTripService.getHistoricalTripByTripId( tripId );
        
        assertFalse( result.isPresent() );
        verify( historicalTripRepository, times( 1 ) ).findByTripId( tripId );
    }
    
    
    @Test
    void testGetAllHistoricalTripsPaginated_Success()
    {
        HistoricalTrip historicalTrip = new HistoricalTrip();
        historicalTrip.setId( 1L );
        Page<HistoricalTrip> historicalTripPage = new PageImpl<>( Collections.singletonList( historicalTrip ) );
        
        when( historicalTripRepository.findAll( any( PageRequest.class ) ) ).thenReturn( historicalTripPage );
        
        Page<HistoricalTripDto> result = historicalTripService.getAllHistoricalTripsPaginated( 0, 10 );
        
        assertFalse( result.isEmpty() );
        assertEquals( 1, result.getContent().size() );
        verify( historicalTripRepository, times( 1 ) ).findAll( any( PageRequest.class ) );
    }
    
    
    @Test
    void testCreateHistoricalTrip_Success()
    {
        HistoricalTripDto historicalTrip = new HistoricalTripDto();
        historicalTrip.setId( 1L );
        
        when( historicalTripRepository.save( any( HistoricalTrip.class ) ) ).thenReturn( BuildEntity.buildHistoricalTrip( historicalTrip ) );
        
        Optional<HistoricalTripDto> result = historicalTripService.createHistoricalTrip( historicalTrip );
        
        assertTrue( result.isPresent() );
        assertEquals( 1L, result.get().getId() );
        verify( historicalTripRepository, times( 1 ) ).save( any( HistoricalTrip.class ) );
    }
    
    
    @Test
    void testUpdateHistoricalTrip_Success()
    {
        Long id = 1L;
        HistoricalTrip existingTrip = new HistoricalTrip();
        existingTrip.setId( id );
        HistoricalTrip updatedTrip = new HistoricalTrip();
        updatedTrip.setTitle( "Updated Title" );
        
        when( historicalTripRepository.findById( id ) ).thenReturn( Optional.of( existingTrip ) );
        when( historicalTripRepository.save( any( HistoricalTrip.class ) ) ).thenReturn( updatedTrip );
        
        Optional<HistoricalTripDto> result = historicalTripService.updateHistoricalTrip( id, updatedTrip );
        
        assertTrue( result.isPresent() );
        assertEquals( "Updated Title", result.get().getTitle() );
        verify( historicalTripRepository, times( 1 ) ).findById( id );
        verify( historicalTripRepository, times( 1 ) ).save( any( HistoricalTrip.class ) );
    }
    
    
    @Test
    void testDeleteHistoricalTrip_Success()
    {
        Long id = 1L;
        HistoricalTrip historicalTrip = new HistoricalTrip();
        historicalTrip.setId( id );
        
        when( historicalTripRepository.findById( id ) ).thenReturn( Optional.of( historicalTrip ) );
        doNothing().when( historicalTripRepository ).deleteById( id );
        
        Optional<HistoricalTripDto> result = historicalTripService.deleteHistoricalTrip( id );
        
        assertTrue( result.isPresent() );
        assertEquals( id, result.get().getId() );
        verify( historicalTripRepository, times( 1 ) ).findById( id );
        verify( historicalTripRepository, times( 1 ) ).deleteById( id );
    }
}