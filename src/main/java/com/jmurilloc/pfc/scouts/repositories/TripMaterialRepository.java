package com.jmurilloc.pfc.scouts.repositories;

import com.jmurilloc.pfc.scouts.entities.TripMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TripMaterialRepository extends JpaRepository<TripMaterial, Long>
{
    
    Optional<TripMaterial> findByTripIdAndProductId( Long tripId, Long productId );
}
