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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    
    
    @GetMapping( "/{id}" )
    public ResponseEntity<NewDto> getNewById( @PathVariable Long id )
    {
        log.info( "Seraching New with id {}", id );
        return newService.getNewById( id ).map( ResponseEntity::ok ).orElseThrow( () -> new NewNotFoundException( MessageError.NEW_NOT_FOUND.getValue() ) );
    }
    
    
    @GetMapping
    public ResponseEntity<List<NewDto>> getAllNews()
    {
        log.info( "Searching all News" );
        List<NewDto> news = newService.getAllNews();
        if( news.isEmpty() )
        {
            throw new NewNotFoundException( MessageError.NEW_NOT_FOUND.getValue() );
        }
        return ResponseEntity.ok( news );
    }
    
    
    @GetMapping( "/paginated" )
    public ResponseEntity<Page<NewDto>> getAllNewsPaginated( Pageable pageable )
    {
        log.info( "Searching all news pagination" );
        Page<NewDto> news = newService.listAllNews( pageable );
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
    
    
    @PreAuthorize( "hasRole('SCOUTER')" )
    @PutMapping( "/{id}" )
    public ResponseEntity<Object> updateNew( @PathVariable Long id, @Valid @RequestBody New newEntity, BindingResult bindingResult )
    {
        if( bindingResult.hasErrors() )
        {
            log.error( "Validation errors: {}", bindingResult.getAllErrors() );
            return UtilValidation.validation( bindingResult );
        }
        Optional<NewDto> updatedNew = newService.updateNew( id, newEntity );
        if( updatedNew.isEmpty() )
        {
            log.error( "New not updated" );
            throw new NewNotCreateException( MessageError.NEW_NOT_CREATE.getValue() );
        }
        return ResponseEntity.ok( updatedNew.get() );
    }
    
    
    @PreAuthorize( "hasRole('SCOUTER')" )
    @DeleteMapping( "/{id}" )
    public ResponseEntity<Object> deleteNew( @PathVariable Long id )
    {
        log.info( "Deleting New with id {}", id );
        Optional<NewDto> deletedNew = newService.deleteNew( id );
        if( deletedNew.isEmpty() )
        {
            log.error( "New not deleted" );
            throw new NewNotFoundException( MessageError.NEW_NOT_FOUND.getValue() );
        }
        return ResponseEntity.ok( deletedNew.get() );
    }
}
