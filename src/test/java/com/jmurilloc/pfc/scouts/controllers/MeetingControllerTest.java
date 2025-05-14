package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.entities.Affiliate;
import com.jmurilloc.pfc.scouts.entities.Meeting;
import com.jmurilloc.pfc.scouts.exceptions.AffiliateInMeetingException;
import com.jmurilloc.pfc.scouts.exceptions.MeetingNotFoundException;
import com.jmurilloc.pfc.scouts.services.AffiliateService;
import com.jmurilloc.pfc.scouts.services.MeetingService;
import com.jmurilloc.pfc.scouts.util.MessageError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
class MeetingControllerTest
{
    @Mock
    private MeetingService meetingService;
    
    @Mock
    private AffiliateService affiliateService;
    
    @InjectMocks
    private MeetingController meetingController;
    
    @Test
    void indexTest() {
        List<Meeting> meetings = List.of(new Meeting(), new Meeting());
        when(meetingService.findAll()).thenReturn(meetings);
        
        List<Meeting> result = meetingController.index();
        
        assertEquals(2, result.size());
        verify(meetingService).findAll();
    }
    
    @Test
    void indexTest_NotFound() {
        when(meetingService.findAll()).thenReturn( Collections.emptyList());
        
        MeetingNotFoundException exception = assertThrows(MeetingNotFoundException.class, meetingController::index);
        
        assertEquals( MessageError.MEEATING_NOT_FOUND.getValue(), exception.getMessage());
        verify(meetingService).findAll();
    }
    
    @Test
    void findByIdTest() {
        Long meetingId = 1L;
        Meeting meeting = new Meeting();
        when(meetingService.findById(meetingId)).thenReturn( Optional.of(meeting));
        
        Meeting result = meetingController.finById(meetingId);
        
        assertNotNull(result);
        verify(meetingService).findById(meetingId);
    }
    
    @Test
    void findByIdTest_NotFound() {
        Long meetingId = 1L;
        when(meetingService.findById(meetingId)).thenReturn(Optional.empty());
        
        MeetingNotFoundException exception = assertThrows(MeetingNotFoundException.class, () -> meetingController.finById(meetingId));
        
        assertEquals(MessageError.MEEATING_NOT_FOUND.getValue(), exception.getMessage());
        verify(meetingService).findById(meetingId);
    }
    
    @Test
    void enrollMeetingToAffiliateTest() {
        Long meetingId = 1L;
        Long affiliateId = 2L;
        
        Meeting meeting = new Meeting();
        Affiliate affiliate = new Affiliate();
        Set<Affiliate> affiliates = new HashSet<>();
        meeting.setEducandos(affiliates);
        
        when(meetingService.findById(meetingId)).thenReturn(Optional.of(meeting));
        when(affiliateService.findById(affiliateId)).thenReturn(Optional.of(affiliate));
        
        ResponseEntity<Object> response = meetingController.enrollMeetingToAffiliate(meetingId, affiliateId);
        
        assertEquals( HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Educando añadido correctamente", response.getBody());
        assertTrue(meeting.getEducandos().contains(affiliate));
        verify(meetingService).findById(meetingId);
        verify(affiliateService).findById(affiliateId);
        verify(meetingService).save(meeting);
    }
    
    @Test
    void enrollMeetingToAffiliateTest_AffiliateAlreadyInMeeting() {
        Long meetingId = 1L;
        Long affiliateId = 2L;
        
        Meeting meeting = new Meeting();
        Affiliate affiliate = new Affiliate();
        Set<Affiliate> affiliates = new HashSet<>();
        affiliates.add(affiliate);
        meeting.setEducandos(affiliates);
        
        when(meetingService.findById(meetingId)).thenReturn(Optional.of(meeting));
        when(affiliateService.findById(affiliateId)).thenReturn(Optional.of(affiliate));
        
        AffiliateInMeetingException exception = assertThrows(AffiliateInMeetingException.class, () -> meetingController.enrollMeetingToAffiliate(meetingId, affiliateId));
        
        assertEquals(MessageError.AFFILIATE_IN_MEETING.getValue(), exception.getMessage());
        verify(meetingService).findById(meetingId);
        verify(affiliateService).findById(affiliateId);
        verify(meetingService, never()).save(meeting);
    }
    
    @Test
    void enrollMeetingToAffiliateTest_MeetingNotFound() {
        Long meetingId = 1L;
        Long affiliateId = 2L;
        
        when(meetingService.findById(meetingId)).thenReturn(Optional.empty());
        
        MeetingNotFoundException exception = assertThrows(MeetingNotFoundException.class, () -> meetingController.enrollMeetingToAffiliate(meetingId, affiliateId));
        
        assertEquals(MessageError.MEEATING_NOT_FOUND.getValue(), exception.getMessage());
        verify(meetingService).findById(meetingId);
        verify(affiliateService, never()).findById(affiliateId);
    }
}