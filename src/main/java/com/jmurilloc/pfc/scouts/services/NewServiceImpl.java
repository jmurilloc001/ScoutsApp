package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.New;
import com.jmurilloc.pfc.scouts.entities.dto.NewDto;
import com.jmurilloc.pfc.scouts.repositories.NewRepository;
import com.jmurilloc.pfc.scouts.services.interfaces.NewService;
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
        log.info( "Not found New with id: {}", id );
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
        log.info( "Updating new with id: {}", id );
        Optional<New> entityOptional = newRepository.findById( id );
        if( entityOptional.isEmpty() )
        {
            log.error( "New not found with id: {}", id );
            return Optional.empty();
        }
        New entity = entityOptional.get();
        entity.setTitle( newEntity.getTitle() );
        entity.setUrlImage( newEntity.getUrlImage() );
        entity.setDescription( newEntity.getDescription() );
        entity.setDate( newEntity.getDate() );
        
        New updatedNew = newRepository.save( entity );
        if( updatedNew.getId() != null )
        {
            log.info( "New updated with id : {}", updatedNew.getId() );
            return Optional.of( BuildDto.buildDto( updatedNew ) );
        }
        log.info( "Error updating New with id: {}", id );
        return Optional.empty();
    }
    
    
    @Transactional
    @Override
    public Optional<NewDto> deleteNew( Long id )
    {
        Optional<New> entityOptional = newRepository.findById( id );
        if( entityOptional.isPresent() )
        {
            log.info( "Deleting new with id: {}", id );
            newRepository.deleteById( id );
            log.info( "New deleted with id: {}", id );
            return Optional.of( BuildDto.buildDto( entityOptional.get() ) );
        }
        log.error( "New not found with id: {}", id );
        return Optional.empty();
    }
    
    
    @Transactional( readOnly = true )
    @Override
    public List<NewDto> getAllNews()
    {
        log.info( "Getting all news" );
        List<New> news = newRepository.findAll();
        if( news.isEmpty() )
        {
            log.info( "No news found" );
            return List.of();
        }
        log.info( "Found {} news", news.size() );
        return BuildDto.buildListDto( news );
    }
    
    
    @Transactional( readOnly = true )
    @Override
    public Page<NewDto> listAllNews( Pageable pageable )
    {
        log.info( "Getting all news with pagination" );
        Page<New> news = newRepository.findAll( pageable );
        if( news.isEmpty() )
        {
            log.info( "No news found" );
            return Page.empty();
        }
        log.info( "Found {} news", news.getTotalElements() );
        return news.map( BuildDto::buildDto );
    }
}
