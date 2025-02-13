package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.entities.Meeting;

import com.jmurilloc.pfc.scouts.exceptions.MettingNotFound;
import com.jmurilloc.pfc.scouts.services.MeetingService;
import com.jmurilloc.pfc.scouts.util.MessageError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

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
            throw new MettingNotFound(MessageError.MEEATING_NOT_FOUND.getValue());
        }
        return meetings;
    }
    @GetMapping("/{id}")
    public Meeting finById(@PathVariable Long id){
        Optional<Meeting> optionalMeeting = service.findById(id);
        if (optionalMeeting.isPresent()){
            return optionalMeeting.get();
        }
        throw new MettingNotFound(MessageError.MEEATING_NOT_FOUND.getValue());
    }

    @PostMapping("/{meetingId}/affiliates/{affiliateId}") //Añade un educando a una reunion, pero no se mete en la nueva tabla
    public void enrollMeetingToAffiliate(
            @PathVariable Long meetingId,
            @PathVariable Long affiliateId
    ){

    }
}
