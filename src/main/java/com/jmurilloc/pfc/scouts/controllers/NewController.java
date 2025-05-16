package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.entities.New;
import com.jmurilloc.pfc.scouts.entities.dto.NewDto;
import com.jmurilloc.pfc.scouts.exceptions.NewNotCreateException;
import com.jmurilloc.pfc.scouts.exceptions.NewNotFoundException;
import com.jmurilloc.pfc.scouts.services.NewService;
import com.jmurilloc.pfc.scouts.util.MessageError;
import com.jmurilloc.pfc.scouts.util.UtilValidation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping( "/news" )
@Slf4j
public class NewController
{
    
    private NewService newService;
    
    
    @Autowired
    public void setNewService( NewService newService )
    {
        this.newService = newService;
    }
    
    
    @GetMapping( "/id" )
    public ResponseEntity<NewDto> getNewById( Long id )
    {
        return newService.getNewById( id ).map( ResponseEntity::ok ).orElseThrow( () -> new NewNotFoundException( MessageError.NEW_NOT_FOUND.getValue() ) );
    }
    
    
    @GetMapping
    public ResponseEntity<List<NewDto>> getAllNews()
    {
        List<NewDto> news = newService.getAllNews();
        if( news.isEmpty() )
        {
            throw new NewNotFoundException( MessageError.NEW_NOT_FOUND.getValue() );
        }
        return ResponseEntity.ok( news );
    }
    
    
    @PreAuthorize( "hasRole('SCOUTER')" )
    @PostMapping
    public ResponseEntity<Object> createNew( @Valid @RequestBody New newEntity, BindingResult bindingResult )
    {
        if( bindingResult.hasErrors() )
        {
            log.error( "Validation errors: {}", bindingResult.getAllErrors() );
            return UtilValidation.validation( bindingResult );
        }
        Optional<NewDto> saveNew = newService.createNew( newEntity );
        if( saveNew.isEmpty() )
        {
            log.error( "New not created" );
            throw new NewNotCreateException( MessageError.NEW_NOT_CREATE.getValue() );
        }
        return ResponseEntity.status( HttpStatus.CREATED ).body( saveNew.get() );
    }
    
}
