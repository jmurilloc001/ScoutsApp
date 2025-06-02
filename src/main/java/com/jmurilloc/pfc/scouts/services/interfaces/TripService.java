package com.jmurilloc.pfc.scouts.services.interfaces;

import com.jmurilloc.pfc.scouts.entities.dto.TripDto;
import com.jmurilloc.pfc.scouts.exceptions.BadDataException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TripService
{
    
    Optional<TripDto> getTripById( Long id );
    
    
    Optional<TripDto> getTripByTitle( String title );
    
    
    List<TripDto> getAllTrips();
    
    
    Page<TripDto> getAllTripsPaginated( int page, int size );
    
    
    Optional<TripDto> createTrip( TripDto tripDto );
    
    
    Optional<TripDto> updateTrip( Long id, TripDto tripDto );
    
    
    Optional<TripDto> deleteTrip( Long id );
    
    
    Optional<TripDto> addProductToTrip( Long tripId, Long productId, Integer quantity );
    
    
    Optional<TripDto> closeTrip( Long tripId, Map<String, Integer> cantidadDevuelta ) throws BadDataException;
    
    
    Optional<TripDto> updateTripMaterial( Long tripId, Long productId, Integer newQuantity );
    
    
    Optional<TripDto> updateTripMaterialByName( Long tripId, String productName, Integer newQuantity );
    
    
    Page<TripDto> getTripsPaginatedByCloseFalse( int page, int size );
}
