package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.New;
import com.jmurilloc.pfc.scouts.entities.dto.NewDto;
import com.jmurilloc.pfc.scouts.repositories.NewRepository;
import com.jmurilloc.pfc.scouts.util.BuildDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class NewServiceImpl implements NewService
{
    
    private NewRepository newRepository;
    
    
    @Autowired
    public void setNewRepository( NewRepository newRepository )
    {
        this.newRepository = newRepository;
    }
    
    
    @Transactional( readOnly = true )
    @Override
    public Optional<NewDto> getNewById( Long id )
    {
        log.info( "Searching new with id: {}", id );
        
        Optional<New> newEntity = newRepository.findById( id );
        if( newEntity.isPresent() )
        {
            log.info( "New found: {}", newEntity.get() );
            return Optional.of( BuildDto.buildDto( newEntity.get() ) );
        }
        return Optional.empty();
    }
    
    
    @Transactional
    @Override
    public Optional<NewDto> createNew( New newEntity )
    {
        log.info( "Creating new New" );
        if( newEntity.getId() != null )
        {
            log.error( "New id must be null" );
            return Optional.empty();
        }
        New savedNew = newRepository.save( newEntity );
        if( savedNew.getId() != null )
        {
            log.info( "New created with id : {}", savedNew.getId() );
            return Optional.of( BuildDto.buildDto( savedNew ) );
        }
        return Optional.empty();
    }
    
    
    @Transactional
    @Override
    public Optional<NewDto> updateNew( Long id, New newEntity )
    {
        return Optional.empty();
    }
    
    
    @Transactional
    @Override
    public Optional<NewDto> deleteNew( Long id )
    {
        return Optional.empty();
    }
    
    
    @Transactional( readOnly = true )
    @Override
    public List<NewDto> getAllNews()
    {
        return List.of();
    }
    
    
    @Transactional( readOnly = true )
    @Override
    public Page<NewDto> listAllNews( Pageable pageable )
    {
        return null;
    }
}
