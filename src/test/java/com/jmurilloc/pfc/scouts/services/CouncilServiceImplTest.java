package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.Council;
import com.jmurilloc.pfc.scouts.exceptions.CouncilNotFoundException;
import com.jmurilloc.pfc.scouts.repositories.CouncilRepositorory;
import com.jmurilloc.pfc.scouts.util.MessageError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
class CouncilServiceImplTest
{
    
    @Mock
    private CouncilRepositorory repositorory;
    
    @InjectMocks
    private CouncilServiceImpl councilService;
    
    
    @Test
    void listAllTest()
    {
        List<Council> councils = List.of( new Council(), new Council() );
        when( repositorory.findAll() ).thenReturn( councils );
        
        List<Council> result = councilService.listAll();
        
        assertEquals( 2, result.size() );
        verify( repositorory ).findAll();
    }
    
    
    @Test
    void listAllTest_NotFound()
    {
        when( repositorory.findAll() ).thenReturn( List.of() );
        
        CouncilNotFoundException exception = assertThrows( CouncilNotFoundException.class, councilService::listAll );
        
        assertEquals( MessageError.COUNCIL_NOT_FOUND.getValue(), exception.getMessage() );
        verify( repositorory ).findAll();
    }
    
    
    @Test
    void findByIdTest()
    {
        Long id = 1L;
        Council council = new Council();
        when( repositorory.findById( id ) ).thenReturn( Optional.of( council ) );
        
        Council result = councilService.findById( id );
        
        assertNotNull( result );
        assertEquals( council, result );
        verify( repositorory ).findById( id );
    }
    
    
    @Test
    void findByIdTest_NotFound()
    {
        Long id = 1L;
        when( repositorory.findById( id ) ).thenReturn( Optional.empty() );
        
        CouncilNotFoundException exception = assertThrows( CouncilNotFoundException.class, () -> councilService.findById( id ) );
        
        assertEquals( MessageError.COUNCIL_NOT_FOUND.getValue(), exception.getMessage() );
        verify( repositorory ).findById( id );
    }
    
    
    @Test
    void findByInitialDateTest()
    {
        Date date = new Date();
        List<Council> councils = List.of( new Council(), new Council() );
        when( repositorory.findByFechaInicioBetween( any( Date.class ), any( Date.class ) ) ).thenReturn( councils );
        
        List<Council> result = councilService.findByInitialDate( date );
        
        assertEquals( 2, result.size() );
        verify( repositorory ).findByFechaInicioBetween( any( Date.class ), any( Date.class ) );
    }
    
    
    @Test
    void findByInitialDateTest_NotFound()
    {
        Date date = new Date();
        when( repositorory.findByFechaInicioBetween( any( Date.class ), any( Date.class ) ) ).thenReturn( List.of() );
        
        CouncilNotFoundException exception = assertThrows( CouncilNotFoundException.class, () -> councilService.findByInitialDate( date ) );
        
        assertEquals( MessageError.COUNCIL_NOT_FOUND.getValue(), exception.getMessage() );
        verify( repositorory ).findByFechaInicioBetween( any( Date.class ), any( Date.class ) );
    }
    
    
    @Test
    void deleteByIdTest()
    {
        Long id = 1L;
        Council council = new Council();
        when( repositorory.findById( id ) ).thenReturn( Optional.of( council ) );
        doNothing().when( repositorory ).deleteById( id );
        
        councilService.deleteById( id );
        
        verify( repositorory ).findById( id );
        verify( repositorory ).deleteById( id );
    }
    
    
    @Test
    void deleteByIdTest_NotFound()
    {
        Long id = 1L;
        when( repositorory.findById( id ) ).thenReturn( Optional.empty() );
        
        CouncilNotFoundException exception = assertThrows( CouncilNotFoundException.class, () -> councilService.deleteById( id ) );
        
        assertEquals( MessageError.COUNCIL_NOT_FOUND.getValue(), exception.getMessage() );
        verify( repositorory ).findById( id );
        verify( repositorory, never() ).deleteById( id );
    }
    
    
    @Test
    void saveTest()
    {
        Council council = new Council();
        when( repositorory.save( council ) ).thenReturn( council );
        
        Council result = councilService.save( council );
        
        assertNotNull( result );
        assertEquals( council, result );
        verify( repositorory ).save( council );
    }
}