package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.entities.Affiliate;
import com.jmurilloc.pfc.scouts.entities.dto.AffiliateDto;
import com.jmurilloc.pfc.scouts.exceptions.AffiliateNotFoundException;
import com.jmurilloc.pfc.scouts.services.AffiliateService;
import com.jmurilloc.pfc.scouts.util.BuildDto;
import com.jmurilloc.pfc.scouts.util.MessageError;
import com.jmurilloc.pfc.scouts.util.UtilValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/affiliates")
public class AffiliateController {

    private AffiliateService service;
    // private RestTemplate restTemplate;

    @Autowired
    public AffiliateController(AffiliateService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public List<AffiliateDto> index(){
        List<Affiliate> affiliates = service.findAll();
        if (affiliates.isEmpty()){
            throw new AffiliateNotFoundException(MessageError.AFFILIATE_NOT_FOUND.getValue());
        }
        List<AffiliateDto> affiliateDtos = new ArrayList<>();
        affiliates.forEach(affiliate -> affiliateDtos.add(BuildDto.buildAffiliateDto(affiliate)));
        return affiliateDtos;
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public AffiliateDto findById(@PathVariable Long id){
        Optional<Affiliate> optionalAffiliate = service.findById(id);
        if (optionalAffiliate.isPresent()){
            Affiliate affiliate = optionalAffiliate.orElseThrow();

            return BuildDto.buildAffiliateDto(affiliate);
        }
        throw new AffiliateNotFoundException(MessageError.AFFILIATE_NOT_FOUND.getValue());
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody Affiliate affiliate, BindingResult result){
        if (result.hasFieldErrors()){
            return UtilValidation.validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(affiliate));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        Optional<Affiliate> optionalAffiliate = service.findById(id);
        if (optionalAffiliate.isPresent()){
            Affiliate affiliate = optionalAffiliate.orElseThrow();
            service.delete(affiliate);
        }else return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(MessageError.AFFILIATE_NOT_FOUND.getValue());

        Optional<Affiliate> optionalAffiliatePostDelete = service.findById(id);
        if (optionalAffiliatePostDelete.isEmpty()){
            return ResponseEntity.ok("Affiliado eliminado");
        }
        return ResponseEntity.badRequest().body(MessageError.AFFILIATE_NOT_DELETED.getValue());
    }
}
