package com.jmurilloc.pfc.scouts.services.interfaces;

import com.jmurilloc.pfc.scouts.entities.HistoricalTrip;
import com.jmurilloc.pfc.scouts.entities.dto.HistoricalTripDto;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface HistoricalTripService
{
    
    Optional<HistoricalTripDto> getHistoricalTripById( Long id );
    
    
    Optional<HistoricalTripDto> getHistoricalTripByTripId( Long tripId );
    
    
    Page<HistoricalTripDto> getAllHistoricalTripsPaginated( int page, int size );
    
    
    Optional<HistoricalTripDto> createHistoricalTrip( HistoricalTripDto historicalTripDto );
    
    
    Optional<HistoricalTripDto> updateHistoricalTrip( Long id, HistoricalTrip historicalTrip );
    
    
    Optional<HistoricalTripDto> deleteHistoricalTrip( Long id );
}
