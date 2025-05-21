package com.jmurilloc.pfc.scouts.services.interfaces;

import com.jmurilloc.pfc.scouts.entities.Council;

import java.util.Date;
import java.util.List;

public interface CouncilService
{
    
    List<Council> listAll();
    
    
    Council findById( Long id );
    
    
    List<Council> findByInitialDate( Date date );
    
    
    void deleteById( Long id );
    
    
    Council save( Council council );
}
