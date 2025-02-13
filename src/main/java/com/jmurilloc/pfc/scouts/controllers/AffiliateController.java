package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.entities.Affiliate;
import com.jmurilloc.pfc.scouts.exceptions.AffiliateNotFoundException;
import com.jmurilloc.pfc.scouts.services.AffiliateService;
import com.jmurilloc.pfc.scouts.util.MessageError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping("/affiliates")
public class AffiliateController {

    private AffiliateService service;
    private RestTemplate restTemplate;

    @Autowired
    public AffiliateController(AffiliateService service,RestTemplate restTemplate) {
        this.service = service;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{id}")
    public Affiliate finById(@PathVariable Long id){
        Optional<Affiliate> optionalAffiliate = service.findById(id);
        if (optionalAffiliate.isPresent()){
            return optionalAffiliate.get();
        }
        throw new AffiliateNotFoundException(MessageError.AFFILIATE_NOT_FOUND.getValue());
    }

//    @PostMapping("/{affiliateId}/meetings/{meetingId}") //Añade un educando a una reunion, pero no se mete en la nueva tabla
//    public Affiliate enrollMeetingToAffiliate(
//            @PathVariable Long meetingId,
//            @PathVariable Long affiliateId
//    ){
//        String url = "http://localhost:8080/meetings/"+meetingId;
//        Meeting meeting = restTemplate.getForObject(url, Meeting.class);
//        Optional<Affiliate> optionalAffiliate = service.findById(affiliateId);
//
//        if (optionalAffiliate.isPresent()){
//            Affiliate affiliate = optionalAffiliate.get();
//
//            //si le pongo getReuniones, después me estaría enseñando tdo el rato en bucle las cosas
//            affiliate.getReuniones().add(meeting); //Añado la reunion a la lista
//
//            return service.save(affiliate);
//        }
//        throw new MettingOrAffiliateNotFoundException(MessageError.MEEATING_AND_AFFILIATE_NOT_FOUND.getValue());
//    }
}
