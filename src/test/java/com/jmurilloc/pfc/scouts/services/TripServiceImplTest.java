package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.Product;
import com.jmurilloc.pfc.scouts.entities.Trip;
import com.jmurilloc.pfc.scouts.entities.TripMaterial;
import com.jmurilloc.pfc.scouts.entities.dto.TripDto;
import com.jmurilloc.pfc.scouts.exceptions.BadDataException;
import com.jmurilloc.pfc.scouts.repositories.TripMaterialRepository;
import com.jmurilloc.pfc.scouts.repositories.TripRepository;
import com.jmurilloc.pfc.scouts.services.interfaces.HistoricalTripService;
import com.jmurilloc.pfc.scouts.services.interfaces.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
class TripServiceImplTest
{
    
    @InjectMocks
    private TripServiceImpl tripService;
    
    @Mock
    private TripRepository tripRepository;
    @Mock
    private HistoricalTripService historicalTripService;
    @Mock
    private ProductService productService;
    @Mock
    private TripMaterialRepository tripMaterialRepository;
    
    
    @Test
    void getTripById_found()
    {
        Trip trip = new Trip();
        trip.setId( 1L );
        when( tripRepository.findById( 1L ) ).thenReturn( Optional.of( trip ) );
        Optional<TripDto> result = tripService.getTripById( 1L );
        assertTrue( result.isPresent() );
    }
    
    
    @Test
    void getTripById_notFound()
    {
        when( tripRepository.findById( 1L ) ).thenReturn( Optional.empty() );
        Optional<TripDto> result = tripService.getTripById( 1L );
        assertFalse( result.isPresent() );
    }
    
    
    @Test
    void getAllTripsPaginated_ok()
    {
        Trip trip = new Trip();
        trip.setId( 1L );
        Page<Trip> page = new PageImpl<>( List.of( trip ) );
        when( tripRepository.findAll( any( PageRequest.class ) ) ).thenReturn( page );
        Page<TripDto> result = tripService.getAllTripsPaginated( 0, 10 );
        assertEquals( 1, result.getTotalElements() );
    }
    
    
    @Test
    void addProductToTrip_alreadyExists()
    {
        when( tripMaterialRepository.findByTripIdAndProductId( 1L, 2L ) ).thenReturn( Optional.of( new TripMaterial() ) );
        assertThrows( BadDataException.class, () -> tripService.addProductToTrip( 1L, 2L, 1 ) );
    }
    
    
    @Test
    void updateTripMaterial_notFound()
    {
        when( tripMaterialRepository.findByTripIdAndProductId( 1L, 2L ) ).thenReturn( Optional.empty() );
        assertThrows( BadDataException.class, () -> tripService.updateTripMaterial( 1L, 2L, 1 ) );
    }
    
    
    @Test
    void updateTripMaterial_negativeQuantity()
    {
        TripMaterial tm = new TripMaterial();
        tm.setCantidad( 2 );
        tm.setProduct( new Product() );
        when( tripMaterialRepository.findByTripIdAndProductId( 1L, 2L ) ).thenReturn( Optional.of( tm ) );
        assertThrows( BadDataException.class, () -> tripService.updateTripMaterial( 1L, 2L, -1 ) );
    }
}