package com.jmurilloc.pfc.scouts.services.interfaces;

import com.jmurilloc.pfc.scouts.entities.New;
import com.jmurilloc.pfc.scouts.entities.dto.NewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface NewService
{
    
    Optional<NewDto> getNewById( Long id );
    
    
    Optional<NewDto> createNew( New newEntity );
    
    
    Optional<NewDto> updateNew( Long id, New newEntity );
    
    
    Optional<NewDto> deleteNew( Long id );
    
    
    List<NewDto> getAllNews();
    
    
    Page<NewDto> listAllNews( Pageable pageable );
}
