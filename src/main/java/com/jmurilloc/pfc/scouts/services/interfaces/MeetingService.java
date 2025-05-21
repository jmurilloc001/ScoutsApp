package com.jmurilloc.pfc.scouts.services.interfaces;

import com.jmurilloc.pfc.scouts.entities.Meeting;

import java.util.List;
import java.util.Optional;

public interface MeetingService
{
    
    Optional<Meeting> findById( Long id );
    
    
    Meeting save( Meeting meeting );
    
    
    List<Meeting> findAll();
}
