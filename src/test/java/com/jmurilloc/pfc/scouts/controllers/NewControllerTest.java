package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.entities.dto.NewDto;
import com.jmurilloc.pfc.scouts.exceptions.NewNotFoundException;
import com.jmurilloc.pfc.scouts.services.NewService;
import com.jmurilloc.pfc.scouts.util.MessageError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
class NewControllerTest
{
    
    @Mock
    private NewService newService;
    
    @InjectMocks
    private NewController newController;
    
    
    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks( this );
    }
    
    
    @Test
    void testGetNewById_Success()
    {
        Long id = 1L;
        NewDto newDto = new NewDto();
        newDto.setId( id );
        
        when( newService.getNewById( id ) ).thenReturn( Optional.of( newDto ) );
        
        ResponseEntity<NewDto> response = newController.getNewById( id );
        
        assertNotNull( response );
        assertEquals( 200, response.getStatusCode().value() );
        assertEquals( newDto, response.getBody() );
        verify( newService, times( 1 ) ).getNewById( id );
    }
    
    
    @Test
    void testGetNewById_NotFound()
    {
        Long id = 1L;
        
        when( newService.getNewById( id ) ).thenReturn( Optional.empty() );
        
        NewNotFoundException exception = assertThrows( NewNotFoundException.class, () -> newController.getNewById( id ) );
        
        assertEquals( MessageError.NEW_NOT_FOUND.getValue(), exception.getMessage() );
        verify( newService, times( 1 ) ).getNewById( id );
    }
    
    
    @Test
    void testGetAllNews_Success()
    {
        NewDto newDto = new NewDto();
        List<NewDto> newsList = List.of( newDto );
        
        when( newService.getAllNews() ).thenReturn( newsList );
        
        ResponseEntity<List<NewDto>> response = newController.getAllNews();
        
        assertNotNull( response );
        assertEquals( 200, response.getStatusCode().value() );
        assertEquals( newsList, response.getBody() );
        verify( newService, times( 1 ) ).getAllNews();
    }
    
    
    @Test
    void testGetAllNews_NotFound()
    {
        when( newService.getAllNews() ).thenReturn( Collections.emptyList() );
        
        NewNotFoundException exception = assertThrows( NewNotFoundException.class, () -> newController.getAllNews() );
        
        assertEquals( MessageError.NEW_NOT_FOUND.getValue(), exception.getMessage() );
        verify( newService, times( 1 ) ).getAllNews();
    }
    
    
    @Test
    void testGetAllNewsPaginated_Success()
    {
        Page<NewDto> page = new PageImpl<>( List.of( new NewDto() ) );
        PageRequest pageable = PageRequest.of( 0, 5 );
        
        when( newService.listAllNews( pageable ) ).thenReturn( page );
        
        ResponseEntity<Page<NewDto>> response = newController.getAllNewsPaginated( pageable );
        
        assertNotNull( response );
        assertEquals( 200, response.getStatusCode().value() );
        assertEquals( page, response.getBody() );
        verify( newService, times( 1 ) ).listAllNews( pageable );
    }
    
    
    @Test
    void testGetAllNewsPaginated_NotFound()
    {
        PageRequest pageable = PageRequest.of( 0, 5 );
        
        when( newService.listAllNews( pageable ) ).thenReturn( Page.empty() );
        
        NewNotFoundException exception = assertThrows( NewNotFoundException.class, () -> newController.getAllNewsPaginated( pageable ) );
        
        assertEquals( MessageError.NEW_NOT_FOUND.getValue(), exception.getMessage() );
        verify( newService, times( 1 ) ).listAllNews( pageable );
    }
}