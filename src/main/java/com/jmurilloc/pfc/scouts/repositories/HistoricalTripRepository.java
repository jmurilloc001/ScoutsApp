package com.jmurilloc.pfc.scouts.repositories;

import com.jmurilloc.pfc.scouts.entities.HistoricalTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistoricalTripRepository extends JpaRepository<HistoricalTrip, Long>
{
    
    Optional<HistoricalTrip> findByTripId( Long tripId );
}
