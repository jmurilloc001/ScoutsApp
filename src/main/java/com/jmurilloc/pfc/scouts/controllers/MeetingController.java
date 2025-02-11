package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.entities.Affiliate;
import com.jmurilloc.pfc.scouts.entities.Meeting;
import com.jmurilloc.pfc.scouts.exceptions.MettingNotFound;
import com.jmurilloc.pfc.scouts.exceptions.MettingOrAffiliateNotFoundException;
import com.jmurilloc.pfc.scouts.services.AffiliateService;
import com.jmurilloc.pfc.scouts.services.MeetingService;
import com.jmurilloc.pfc.scouts.util.MessageError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/meeting")
public class MeetingController {

    private MeetingService service;
    private AffiliateService affiliateService;

    @Autowired
    public MeetingController(MeetingService service,AffiliateService affiliateService) {
        this.service = service;
        this.affiliateService = affiliateService;
    }

    @GetMapping
    public List<Meeting> index(){
        List<Meeting> meetings = service.findAll();
        if (meetings.isEmpty()){
            throw new MettingNotFound(MessageError.MEEATING_NOT_FOUND.getValue());
        }
        return meetings;
    }

    @PostMapping("/{meetingId}/affiliate/{affiliateId}") //Añade un educando a una reunion, pero no se mete en la nueva tabla
    public Meeting enrollMeetingToAffiliate(
            @PathVariable Long meetingId,
            @PathVariable Long affiliateId
    ){
        Optional<Meeting> optionalMeeting = service.findById(meetingId);
        Optional<Affiliate> optionalAffiliate = affiliateService.findById(affiliateId);

        if (optionalMeeting.isPresent() && optionalAffiliate.isPresent()){
            Meeting meeting =  optionalMeeting.get();

            meeting.getEducandos().add(optionalAffiliate.get()); //Añado el afiliado a la lista

            return service.save(meeting);
        }
        throw new MettingOrAffiliateNotFoundException(MessageError.MEEATING_AND_AFFILIATE_NOT_FOUND.getValue());
    }
}
