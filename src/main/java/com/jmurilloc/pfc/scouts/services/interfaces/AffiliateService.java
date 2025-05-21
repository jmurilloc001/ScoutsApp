package com.jmurilloc.pfc.scouts.services.interfaces;

import com.jmurilloc.pfc.scouts.entities.Affiliate;

import java.util.List;
import java.util.Optional;

public interface AffiliateService
{
    
    Optional<Affiliate> findById( Long id );
    
    
    Affiliate save( Affiliate affiliate );
    
    
    List<Affiliate> findAll();
    
    
    void delete( Affiliate affiliate );
    
    
    Optional<Affiliate> findByName( String name );
}
