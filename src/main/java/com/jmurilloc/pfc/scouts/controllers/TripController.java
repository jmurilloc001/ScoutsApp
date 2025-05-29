package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.entities.dto.TripDto;
import com.jmurilloc.pfc.scouts.exceptions.TripNotFoundException;
import com.jmurilloc.pfc.scouts.services.interfaces.TripService;
import com.jmurilloc.pfc.scouts.util.MessageError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping( "/trips" )
public class TripController
{
    
    private TripService tripService;
    
    
    @Autowired
    public void setTripService( TripService tripService )
    {
        this.tripService = tripService;
    }
    
    
    @PreAuthorize( "hasRole('SCOUTER')" )
    @GetMapping( "/{id}" )
    public ResponseEntity<TripDto> getTripById( @PathVariable Long id )
    {
        log.info( "Fetching trips" );
        Optional<TripDto> optionalTripDto = tripService.getTripById( id );
        if( optionalTripDto.isPresent() )
        {
            log.info( "Trip found with id: {}", id );
            return ResponseEntity.ok( optionalTripDto.get() );
        }
        else
        {
            log.warn( "Trip not found with id: {}", id );
            throw new TripNotFoundException( MessageError.TRIP_NOT_FOUND.getValue() );
        }
    }
    
    
    @PreAuthorize( "hasRole('SCOUTER')" )
    @GetMapping( "/paginated" )
    public ResponseEntity<Page<TripDto>> getAllTripsPaginated( @RequestParam int page, @RequestParam int size )
    {
        log.info( "Fetching all trips with pagination: page {}, size {}", page, size );
        Page<TripDto> tripPage = tripService.getAllTripsPaginated( page, size );
        if( tripPage.hasContent() )
        {
            log.info( "Trips found: {}", tripPage.getTotalElements() );
            return ResponseEntity.ok( tripPage );
        }
        else
        {
            log.warn( "No trips found" );
            throw new TripNotFoundException( MessageError.TRIP_NOT_FOUND.getValue() );
        }
    }
    
    
    @PreAuthorize( "hasRole('SCOUTER')" )
    @PostMapping
    public ResponseEntity<TripDto> createTrip( @RequestBody TripDto tripDto )
    {
        try
        {
            if( tripDto.getMaterials() == null || tripDto.getMaterials().isEmpty() )
            {
                log.info( "No products provided for trip, initializing empty product list." );
                tripDto.setMaterials( new HashSet<>() );
            }
            
            log.info( "Creating trip with title: {}", tripDto.getTitle() );
            
            Optional<TripDto> optionalTripDto = tripService.createTrip( tripDto );
            if( optionalTripDto.isPresent() )
            {
                log.info( "Trip created successfully with id: {}", optionalTripDto.get().getId() );
                return ResponseEntity.status( HttpStatus.CREATED ).body( optionalTripDto.get() );
            }
            else
            {
                log.warn( "Failed to create trip" );
                throw new TripNotFoundException( MessageError.TRIP_NOT_CREATED.getValue() );
            }
        }
        catch ( Exception e )
        {
            log.error( "Error creating trip: {}", e.getMessage() );
            return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).build();
        }
    }
    
    
    @PreAuthorize( "hasRole('SCOUTER')" )
    @PutMapping( "/{id}" )
    public ResponseEntity<TripDto> updateTrip( @PathVariable Long id, TripDto tripDto )
    {
        log.info( "Updating trip with id: {}", id );
        Optional<TripDto> optionalTripDto = tripService.updateTrip( id, tripDto );
        if( optionalTripDto.isPresent() )
        {
            log.info( "Trip updated successfully with id: {}", optionalTripDto.get().getId() );
            return ResponseEntity.status( HttpStatus.CREATED ).body( optionalTripDto.get() );
        }
        else
        {
            log.warn( "Failed to update trip with id: {}", id );
            throw new TripNotFoundException( MessageError.TRIP_NOT_FOUND.getValue() );
        }
    }
    
    
    @PreAuthorize( "hasRole('SCOUTER')" )
    @PutMapping( "/{id}/addProduct" )
    public ResponseEntity<TripDto> addProduct( @PathVariable Long id, @RequestParam Long productId, @RequestParam Integer quantity )
    {
        log.info( "Adding product with id: {} to trip with id: {}", productId, id );
        Optional<TripDto> optionalTripDto = tripService.addProductToTrip( id, productId, quantity );
        if( optionalTripDto.isPresent() )
        {
            log.info( "Product added successfully to trip with id: {}", id );
            return ResponseEntity.status( HttpStatus.CREATED ).body( optionalTripDto.get() );
        }
        else
        {
            log.warn( "Failed to add product to trip with id: {}", id );
            throw new TripNotFoundException( MessageError.TRIP_NOT_FOUND.getValue() );
        }
    }
    
    
    @PreAuthorize( "hasRole('SCOUTER')" )
    @DeleteMapping( "/{id}" )
    public ResponseEntity<TripDto> deleteTrip( @PathVariable Long id )
    {
        log.info( "Deleting trip with id: {}", id );
        Optional<TripDto> optionalTripDto = tripService.deleteTrip( id );
        if( optionalTripDto.isPresent() )
        {
            log.info( "Trip deleted successfully with id: {}", id );
            return ResponseEntity.ok( optionalTripDto.get() );
        }
        else
        {
            log.warn( "Failed to delete trip with id: {}", id );
            throw new TripNotFoundException( MessageError.TRIP_NOT_FOUND.getValue() );
        }
    }
}
