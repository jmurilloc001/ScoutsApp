package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.entities.Affiliate;
import com.jmurilloc.pfc.scouts.entities.dto.AffiliateDto;
import com.jmurilloc.pfc.scouts.exceptions.AffiliateNotFoundException;
import com.jmurilloc.pfc.scouts.exceptions.UserNotFoundException;
import com.jmurilloc.pfc.scouts.services.AffiliateService;
import com.jmurilloc.pfc.scouts.util.MessageError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
class AffiliateControllerTest
{
    
    @Mock
    private AffiliateService affiliateService;
    
    @InjectMocks
    private AffiliateController affiliateController;
    
    
    @Test
    void indexTest()
    {
        List<Affiliate> affiliates = List.of( new Affiliate(), new Affiliate() );
        when( affiliateService.findAll() ).thenReturn( affiliates );
        
        List<AffiliateDto> result = affiliateController.index();
        
        assertEquals( 2, result.size() );
        verify( affiliateService ).findAll();
    }
    
    
    @Test
    void indexTest_NotFound()
    {
        when( affiliateService.findAll() ).thenReturn( new ArrayList<>() );
        
        AffiliateNotFoundException exception = assertThrows( AffiliateNotFoundException.class, affiliateController::index );
        
        assertEquals( MessageError.AFFILIATE_NOT_FOUND.getValue(), exception.getMessage() );
        verify( affiliateService ).findAll();
    }
    
    
    @Test
    void findByIdTest()
    {
        Long id = 1L;
        Affiliate affiliate = new Affiliate();
        when( affiliateService.findById( id ) ).thenReturn( Optional.of( affiliate ) );
        
        AffiliateDto result = affiliateController.findById( id );
        
        assertNotNull( result );
        verify( affiliateService ).findById( id );
    }
    
    
    @Test
    void findByIdTest_NotFound()
    {
        Long id = 1L;
        when( affiliateService.findById( id ) ).thenReturn( Optional.empty() );
        
        AffiliateNotFoundException exception = assertThrows( AffiliateNotFoundException.class, () -> affiliateController.findById( id ) );
        
        assertEquals( MessageError.AFFILIATE_NOT_FOUND.getValue(), exception.getMessage() );
        verify( affiliateService ).findById( id );
    }
    
    
    @Test
    void findByNameTest()
    {
        String name = "John";
        Affiliate affiliate = new Affiliate();
        when( affiliateService.findByName( name ) ).thenReturn( Optional.of( affiliate ) );
        
        AffiliateDto result = affiliateController.findByName( name );
        
        assertNotNull( result );
        verify( affiliateService ).findByName( name );
    }
    
    
    @Test
    void findByNameTest_NotFound()
    {
        String name = "John";
        when( affiliateService.findByName( name ) ).thenReturn( Optional.empty() );
        
        AffiliateNotFoundException exception = assertThrows( AffiliateNotFoundException.class, () -> affiliateController.findByName( name ) );
        
        assertEquals( MessageError.AFFILIATE_NOT_FOUND.getValue(), exception.getMessage() );
        verify( affiliateService ).findByName( name );
    }
    
    
    @Test
    void createTest()
    {
        Affiliate affiliate = new Affiliate();
        BindingResult result = mock( BindingResult.class );
        when( result.hasFieldErrors() ).thenReturn( false );
        when( affiliateService.save( affiliate ) ).thenReturn( affiliate );
        
        ResponseEntity<Object> response = affiliateController.create( affiliate, result );
        
        assertEquals( HttpStatus.CREATED, response.getStatusCode() );
        verify( affiliateService ).save( affiliate );
    }
    
    
    @Test
    void createTest_ValidationError()
    {
        Affiliate affiliate = new Affiliate();
        BindingResult result = mock( BindingResult.class );
        when( result.hasFieldErrors() ).thenReturn( true );
        
        ResponseEntity<Object> response = affiliateController.create( affiliate, result );
        
        assertEquals( HttpStatus.BAD_REQUEST, response.getStatusCode() );
        verify( affiliateService, never() ).save( affiliate );
    }
    
    
    @Test
    void deleteTest()
    {
        Long id = 1L;
        Affiliate affiliate = new Affiliate();
        when( affiliateService.findById( id ) ).thenReturn( Optional.of( affiliate ) ).thenReturn( Optional.empty() );
        
        ResponseEntity<String> response = affiliateController.delete( id );
        
        assertEquals( HttpStatus.OK, response.getStatusCode() );
        assertEquals( "Afiliado eliminado", response.getBody() );
        verify( affiliateService ).delete( affiliate );
    }
    
    
    @Test
    void deleteTest_NotFound()
    {
        Long id = 1L;
        when( affiliateService.findById( id ) ).thenReturn( Optional.empty() );
        
        ResponseEntity<String> response = affiliateController.delete( id );
        
        assertEquals( HttpStatus.NOT_FOUND, response.getStatusCode() );
        assertEquals( MessageError.AFFILIATE_NOT_FOUND.getValue(), response.getBody() );
        verify( affiliateService, never() ).delete( any() );
    }
    
    
    @Test
    void updateTest()
    {
        Long id = 1L;
        Affiliate affiliate = new Affiliate();
        affiliate.setName( "Updated Name" );
        BindingResult result = mock( BindingResult.class );
        when( result.hasFieldErrors() ).thenReturn( false );
        when( affiliateService.findById( id ) ).thenReturn( Optional.of( new Affiliate() ) );
        when( affiliateService.save( any( Affiliate.class ) ) ).thenReturn( affiliate );
        
        ResponseEntity<Object> response = affiliateController.update( affiliate, result, id );
        
        assertEquals( HttpStatus.CREATED, response.getStatusCode() );
        verify( affiliateService ).save( any( Affiliate.class ) );
    }
    
    
    @Test
    void updateTest_NotFound()
    {
        Long id = 1L;
        Affiliate affiliate = new Affiliate();
        BindingResult result = mock( BindingResult.class );
        when( result.hasFieldErrors() ).thenReturn( false );
        when( affiliateService.findById( id ) ).thenReturn( Optional.empty() );
        
        UserNotFoundException exception = assertThrows( UserNotFoundException.class, () -> affiliateController.update( affiliate, result, id ) );
        
        assertEquals( MessageError.AFFILIATE_NOT_FOUND.getValue(), exception.getMessage() );
        verify( affiliateService, never() ).save( any() );
    }
    
    
    @Test
    void updateImportantDataTest()
    {
        Long id = 1L;
        Affiliate affiliate = new Affiliate();
        affiliate.setSeccion( "Updated Section" );
        BindingResult result = mock( BindingResult.class );
        when( result.hasFieldErrors() ).thenReturn( false );
        when( affiliateService.findById( id ) ).thenReturn( Optional.of( new Affiliate() ) );
        when( affiliateService.save( any( Affiliate.class ) ) ).thenReturn( affiliate );
        
        ResponseEntity<Object> response = affiliateController.updateImportantData( affiliate, result, id );
        
        assertEquals( HttpStatus.OK, response.getStatusCode() );
        verify( affiliateService ).save( any( Affiliate.class ) );
    }
    
    
    @Test
    void updateImportantDataTest_NotFound()
    {
        Long id = 1L;
        Affiliate affiliate = new Affiliate();
        BindingResult result = mock( BindingResult.class );
        when( result.hasFieldErrors() ).thenReturn( false );
        when( affiliateService.findById( id ) ).thenReturn( Optional.empty() );
        
        UserNotFoundException exception = assertThrows( UserNotFoundException.class, () -> affiliateController.updateImportantData( affiliate, result, id ) );
        
        assertEquals( MessageError.AFFILIATE_NOT_FOUND.getValue(), exception.getMessage() );
        verify( affiliateService, never() ).save( any() );
    }
}