package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.Affiliate;
import com.jmurilloc.pfc.scouts.entities.User;
import com.jmurilloc.pfc.scouts.repositories.AffiliatesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
class AffiliateServiceImplTest
{
    
    @Mock
    private AffiliatesRepository repository;
    
    @InjectMocks
    private AffiliateServiceImpl affiliateService;
    
    
    @Test
    void findByIdTest()
    {
        Long id = 1L;
        Affiliate affiliate = new Affiliate();
        when( repository.findById( id ) ).thenReturn( Optional.of( affiliate ) );
        
        Optional<Affiliate> result = affiliateService.findById( id );
        
        assertTrue( result.isPresent() );
        assertEquals( affiliate, result.get() );
        verify( repository ).findById( id );
    }
    
    
    @Test
    void findByIdTest_NotFound()
    {
        Long id = 1L;
        when( repository.findById( id ) ).thenReturn( Optional.empty() );
        
        Optional<Affiliate> result = affiliateService.findById( id );
        
        assertFalse( result.isPresent() );
        verify( repository ).findById( id );
    }
    
    
    @Test
    void saveTest()
    {
        Affiliate affiliate = new Affiliate();
        when( repository.save( affiliate ) ).thenReturn( affiliate );
        
        Affiliate result = affiliateService.save( affiliate );
        
        assertNotNull( result );
        assertEquals( affiliate, result );
        verify( repository ).save( affiliate );
    }
    
    
    @Test
    void findAllTest()
    {
        List<Affiliate> affiliates = List.of( new Affiliate(), new Affiliate() );
        when( repository.findAll() ).thenReturn( affiliates );
        
        List<Affiliate> result = affiliateService.findAll();
        
        assertEquals( 2, result.size() );
        verify( repository ).findAll();
    }
    
    
    @Test
    void deleteTest()
    {
        Affiliate affiliate = mock( Affiliate.class );
        doNothing().when( repository ).delete( affiliate );
        
        affiliateService.delete( affiliate );
        
        verify( repository ).delete( affiliate );
    }
    
    
    @Test
    void deleteWithUser()
    {
        Affiliate affiliate = new Affiliate();
        affiliate.setUser( new User() );
        doNothing().when( repository ).delete( affiliate );
        affiliateService.delete( affiliate );
        verify( repository ).delete( affiliate );
    }
    
    
    @Test
    void findByNameTest()
    {
        String name = "John";
        Affiliate affiliate = new Affiliate();
        when( repository.findByName( name ) ).thenReturn( Optional.of( affiliate ) );
        
        Optional<Affiliate> result = affiliateService.findByName( name );
        
        assertTrue( result.isPresent() );
        assertEquals( affiliate, result.get() );
        verify( repository ).findByName( name );
    }
    
    
    @Test
    void findByNameTest_NotFound()
    {
        String name = "John";
        when( repository.findByName( name ) ).thenReturn( Optional.empty() );
        
        Optional<Affiliate> result = affiliateService.findByName( name );
        
        assertFalse( result.isPresent() );
        verify( repository ).findByName( name );
    }
    
}