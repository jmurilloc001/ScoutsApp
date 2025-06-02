package com.jmurilloc.pfc.scouts.repositories;

import com.jmurilloc.pfc.scouts.entities.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long>
{
    
    Optional<Trip> findByTitle( String title );
    
    
    Page<Trip> findAllByCloseFalse( Pageable pageable );
}
