package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.entities.HistoricalTrip;
import com.jmurilloc.pfc.scouts.entities.dto.HistoricalTripDto;
import com.jmurilloc.pfc.scouts.exceptions.HistoricalTripNotCreatedException;
import com.jmurilloc.pfc.scouts.exceptions.HistoricalTripNotFoundException;
import com.jmurilloc.pfc.scouts.services.interfaces.HistoricalTripService;
import com.jmurilloc.pfc.scouts.util.MessageError;
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

import java.util.Optional;

@RestController
@RequestMapping( "/historical-trips" )
public class HistoricalTripController
{
    
    private HistoricalTripService service;
    
    
    @Autowired
    public void setService( HistoricalTripService service )
    {
        this.service = service;
    }
    
    
    @PreAuthorize( "hasRole('SCOUTER')" )
    @GetMapping( "/{id}" )
    public ResponseEntity<HistoricalTripDto> getHistoricalTripById( @PathVariable Long id )
    {
        Optional<HistoricalTripDto> historicalTrip = service.getHistoricalTripById( id );
        if( historicalTrip.isEmpty() )
        {
            throw new HistoricalTripNotFoundException( MessageError.HISTORICAL_TRIP_NOT_FOUND.getValue() );
        }
        return ResponseEntity.ok( historicalTrip.get() );
    }
    
    
    @PreAuthorize( "hasRole('SCOUTER')" )
    @GetMapping( "/paginated" )
    public ResponseEntity<Page<HistoricalTripDto>> getAllHistoricalTripsPaginated( @RequestParam( defaultValue = "0" ) int page, @RequestParam( defaultValue = "10" ) int size )
    {
        Page<HistoricalTripDto> historicalTrips = service.getAllHistoricalTripsPaginated( page, size );
        if( historicalTrips.isEmpty() )
        {
            throw new HistoricalTripNotFoundException( MessageError.HISTORICAL_TRIP_NOT_FOUND.getValue() );
        }
        return ResponseEntity.ok( historicalTrips );
    }
    
    
    @PreAuthorize( "hasRole('SCOUTER')" )
    @GetMapping( "/trip/{tripId}" )
    public ResponseEntity<HistoricalTripDto> getHistoricalTripByTripId( @PathVariable Long tripId )
    {
        Optional<HistoricalTripDto> historicalTrip = service.getHistoricalTripByTripId( tripId );
        if( historicalTrip.isEmpty() )
        {
            throw new HistoricalTripNotFoundException( MessageError.HISTORICAL_TRIP_NOT_FOUND.getValue() );
        }
        return ResponseEntity.ok( historicalTrip.get() );
    }
    
    
    @PreAuthorize( "hasRole('SCOUTER')" )
    @PostMapping
    public ResponseEntity<HistoricalTripDto> createHistoricalTrip( @RequestBody HistoricalTripDto historicalTripDto )
    {
        Optional<HistoricalTripDto> createdHistoricalTrip = service.createHistoricalTrip( historicalTripDto );
        if( createdHistoricalTrip.isEmpty() )
        {
            throw new HistoricalTripNotCreatedException( MessageError.HISTORICAL_TRIP_NOT_CREATED.getValue() );
        }
        return ResponseEntity.status( HttpStatus.CREATED ).body( createdHistoricalTrip.get() );
    }
    
    
    @PreAuthorize( "hasRole('SCOUTER')" )
    @PutMapping( "/{id}" )
    public ResponseEntity<HistoricalTripDto> updateHistoricalTrip( @PathVariable Long id, @RequestBody HistoricalTrip historicalTrip )
    {
        Optional<HistoricalTripDto> updatedHistoricalTrip = service.updateHistoricalTrip( id, historicalTrip );
        if( updatedHistoricalTrip.isEmpty() )
        {
            throw new HistoricalTripNotCreatedException( MessageError.HISTORICAL_TRIP_NOT_CREATED.getValue() );
        }
        return ResponseEntity.status( HttpStatus.CREATED ).body( updatedHistoricalTrip.get() );
    }
    
    
    @PreAuthorize( "hasRole('SCOUTER')" )
    @DeleteMapping( "/{id}" )
    public ResponseEntity<HistoricalTripDto> deleteHistoricalTrip( @PathVariable Long id )
    {
        Optional<HistoricalTripDto> deletedHistoricalTrip = service.deleteHistoricalTrip( id );
        if( deletedHistoricalTrip.isEmpty() )
        {
            throw new HistoricalTripNotFoundException( MessageError.HISTORICAL_TRIP_NOT_FOUND.getValue() );
        }
        return ResponseEntity.ok( deletedHistoricalTrip.get() );
    }
    
}
