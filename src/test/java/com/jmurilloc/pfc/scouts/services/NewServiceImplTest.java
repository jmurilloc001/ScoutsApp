package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.New;
import com.jmurilloc.pfc.scouts.entities.dto.NewDto;
import com.jmurilloc.pfc.scouts.repositories.NewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith( MockitoExtension.class )
class NewServiceImplTest
{
    
    @InjectMocks
    private NewServiceImpl newService;
    
    @Mock
    private NewRepository newRepository;
    
    
    @Test
    void testGetNewById()
    {
        Long id = 1L;
        New newEntity = new New();
        newEntity.setId( id );
        
        Mockito.when( newRepository.findById( id ) ).thenReturn( Optional.of( newEntity ) );
        Optional<NewDto> result = newService.getNewById( id );
        
        // Assert
        assertNotNull( result );
        assertEquals( id, result.get().getId() );
    }
    
    
    @Test
    void testCreateNew()
    {
        New newEntity = new New();
        newEntity.setId( 1L );
        New entityToSave = new New();
        entityToSave.setId( null );
        
        Mockito.when( newRepository.save( entityToSave ) ).thenReturn( newEntity );
        Optional<NewDto> result = newService.createNew( entityToSave );
        
        // Assert
        assertNotNull( result );
        assertEquals( newEntity.getId(), result.get().getId() );
    }
    
    
    @Test
    void testCreateNewWithId()
    {
        New newEntity = new New();
        newEntity.setId( 1L );
        
        Optional<NewDto> result = newService.createNew( newEntity );
        
        // Assert
        assertNotNull( result );
        assertTrue( result.isEmpty() );
    }
    
    
    @Test
    void testUpdateNew()
    {
        Long id = 1L;
        New newEntity = new New();
        newEntity.setId( id );
        
        Mockito.when( newRepository.findById( id ) ).thenReturn( Optional.of( newEntity ) );
        Mockito.when( newRepository.save( newEntity ) ).thenReturn( newEntity );
        
        Optional<NewDto> result = newService.updateNew( id, newEntity );
        
        // Assert
        assertNotNull( result );
        assertEquals( id, result.get().getId() );
    }
    
    
    @Test
    void testUpdateNewNotFound()
    {
        Long id = 1L;
        New newEntity = new New();
        newEntity.setId( id );
        
        Mockito.when( newRepository.findById( id ) ).thenReturn( Optional.empty() );
        
        Optional<NewDto> result = newService.updateNew( id, newEntity );
        
        // Assert
        assertNotNull( result );
        assertTrue( result.isEmpty() );
    }
    
    
    @Test
    void testDeleteNew()
    {
        Long id = 1L;
        New newEntity = new New();
        newEntity.setId( id );
        
        Mockito.when( newRepository.findById( id ) ).thenReturn( Optional.of( newEntity ) );
        
        Optional<NewDto> result = newService.deleteNew( id );
        
        // Assert
        assertTrue( result.isPresent() );
    }
    
    
    @Test
    void testDeleteNewNotFound()
    {
        Long id = 1L;
        
        Mockito.when( newRepository.findById( id ) ).thenReturn( Optional.empty() );
        
        Optional<NewDto> result = newService.deleteNew( id );
        
        // Assert
        assertTrue( result.isEmpty() );
    }
}