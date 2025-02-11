package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.entities.Affiliate;
import com.jmurilloc.pfc.scouts.entities.Meeting;
import com.jmurilloc.pfc.scouts.exceptions.MettingOrAffiliateNotFoundException;
import com.jmurilloc.pfc.scouts.services.AffiliateService;
import com.jmurilloc.pfc.scouts.services.MeetingService;
import com.jmurilloc.pfc.scouts.util.MessageError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/affiliate")
public class AffiliateController {

    private MeetingService meetingService;
    private AffiliateService service;

    @Autowired
    public AffiliateController(MeetingService meetingService,AffiliateService service) {
        this.service = service;
        this.meetingService = meetingService;
    }

    @PostMapping("/{affiliateId}/meeting/{meetingId}") //Añade un educando a una reunion, pero no se mete en la nueva tabla
    public Affiliate enrollMeetingToAffiliate(
            @PathVariable Long meetingId,
            @PathVariable Long affiliateId
    ){
        Optional<Meeting> optionalMeeting = meetingService.findById(meetingId);
        Optional<Affiliate> optionalAffiliate = service.findById(affiliateId);

        if (optionalMeeting.isPresent() && optionalAffiliate.isPresent()){
            Affiliate affiliate = optionalAffiliate.get();

            //si le pongo getReuniones, después me estaría eneñando tdo el rato en bucle las cosas
            affiliate.conseguirReuniones().add(optionalMeeting.get()); //Añado la reunion a la lista

            return service.save(affiliate);
        }
        throw new MettingOrAffiliateNotFoundException(MessageError.MEEATING_AND_AFFILIATE_NOT_FOUND.getValue());
    }
}
