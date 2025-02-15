package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.entities.Affiliate;
import com.jmurilloc.pfc.scouts.entities.Meeting;

import com.jmurilloc.pfc.scouts.exceptions.AffiliateInMeetingException;
import com.jmurilloc.pfc.scouts.exceptions.AffiliateNotFoundException;
import com.jmurilloc.pfc.scouts.exceptions.MeetingNotFound;
import com.jmurilloc.pfc.scouts.services.MeetingService;
import com.jmurilloc.pfc.scouts.util.MessageError;
import com.jmurilloc.pfc.scouts.util.UrlEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/meetings")
public class MeetingController {

    private RestTemplate restTemplate;
    private MeetingService service;

    @Autowired
    public MeetingController(MeetingService service,RestTemplate restTemplate) {
        this.service = service;
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public List<Meeting> index(){
        List<Meeting> meetings = service.findAll();
        if (meetings.isEmpty()){
            throw new MeetingNotFound(MessageError.MEEATING_NOT_FOUND.getValue());
        }
        return meetings;
    }
    @GetMapping("/{id}")
    public Meeting finById(@PathVariable Long id){
        Optional<Meeting> optionalMeeting = service.findById(id);
        if (optionalMeeting.isPresent()){
            return optionalMeeting.get();
        }
        throw new MeetingNotFound(MessageError.MEEATING_NOT_FOUND.getValue());
    }

    @PostMapping("/{meetingId}/affiliates/{affiliateId}") //Añade un educando a una reunion, pero no se mete en la nueva tabla
    public ResponseEntity<Object> enrollMeetingToAffiliate(
            @PathVariable Long meetingId,
            @PathVariable Long affiliateId
    ){

        Optional<Meeting> optionalMeeting = service.findById(meetingId);
        Affiliate affiliate;
        try {
            affiliate = restTemplate.getForObject(UrlEnum.GET_AFFILIATE_BY_ID.getValue() + affiliateId,Affiliate.class);
        } catch (RestClientException e) {
            throw new AffiliateNotFoundException(MessageError.AFFILIATE_NOT_FOUND.getValue());
        }

        if (optionalMeeting.isPresent()){

            Meeting meeting = optionalMeeting.get();

            Set<Affiliate> affiliates = meeting.getEducandos();
            if (affiliates.contains(affiliate)){
                throw new AffiliateInMeetingException(MessageError.AFFILIATE_IN_MEETING.getValue());
            }
            affiliates.add(affiliate);
            service.save(meeting);
            return ResponseEntity.status(HttpStatus.CREATED).body("Educando añadido correctamente");
        }

        throw new MeetingNotFound(MessageError.MEEATING_NOT_FOUND.getValue());
    }
}
