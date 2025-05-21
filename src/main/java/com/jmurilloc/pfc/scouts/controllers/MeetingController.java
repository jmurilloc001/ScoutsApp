package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.entities.Affiliate;
import com.jmurilloc.pfc.scouts.entities.Meeting;
import com.jmurilloc.pfc.scouts.exceptions.AffiliateInMeetingException;
import com.jmurilloc.pfc.scouts.exceptions.MeetingNotFoundException;
import com.jmurilloc.pfc.scouts.services.interfaces.AffiliateService;
import com.jmurilloc.pfc.scouts.services.interfaces.MeetingService;
import com.jmurilloc.pfc.scouts.util.MessageError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping( "/meetings" )
public class MeetingController
{
    
    private final MeetingService service;
    private final AffiliateService affiliateService;
    
    
    @Autowired
    public MeetingController( MeetingService service, AffiliateService affiliateService )
    {
        this.service = service;
        this.affiliateService = affiliateService;
    }
    
    
    @GetMapping
    public List<Meeting> index()
    {
        List<Meeting> meetings = service.findAll();
        if( meetings.isEmpty() )
        {
            throw new MeetingNotFoundException( MessageError.MEEATING_NOT_FOUND.getValue() );
        }
        return meetings;
    }
    
    
    @PreAuthorize( "hasAnyRole('ADMIN','USER')" )
    @GetMapping( "/{id}" )
    public Meeting finById( @PathVariable Long id )
    {
        Optional<Meeting> optionalMeeting = service.findById( id );
        if( optionalMeeting.isPresent() )
        {
            return optionalMeeting.get();
        }
        throw new MeetingNotFoundException( MessageError.MEEATING_NOT_FOUND.getValue() );
    }
    
    
    @PreAuthorize( "hasRole('ADMIN')" )
    @PatchMapping( "/{meetingId}/affiliates/{affiliateId}" ) //Añade un educando a una reunion, pero no se mete en la nueva tabla
    public ResponseEntity<Object> enrollMeetingToAffiliate( @PathVariable Long meetingId, @PathVariable Long affiliateId )
    {
        
        Optional<Meeting> optionalMeeting = service.findById( meetingId );
        if( optionalMeeting.isPresent() )
        {
            
            Meeting meeting = optionalMeeting.get();
            Optional<Affiliate> optionalAffiliate = affiliateService.findById( affiliateId );
            if( optionalAffiliate.isPresent() )
            {
                
                Affiliate affiliate = optionalAffiliate.orElseThrow();
                
                Set<Affiliate> affiliates = meeting.getEducandos();
                if( affiliates.contains( affiliate ) )
                {
                    throw new AffiliateInMeetingException( MessageError.AFFILIATE_IN_MEETING.getValue() );
                }
                affiliates.add( affiliate );
            }
            
            service.save( meeting );
            return ResponseEntity.status( HttpStatus.CREATED ).body( "Educando añadido correctamente" );
        }
        
        throw new MeetingNotFoundException( MessageError.MEEATING_NOT_FOUND.getValue() );
    }
}
