package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.Meeting;
import com.jmurilloc.pfc.scouts.repositories.MeetingRepository;
import com.jmurilloc.pfc.scouts.services.interfaces.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MeetingServiceImpl implements MeetingService
{
    
    private final MeetingRepository repository;
    
    
    @Autowired
    public MeetingServiceImpl( MeetingRepository repository )
    {
        this.repository = repository;
    }
    
    
    @Transactional( readOnly = true )
    @Override
    public Optional<Meeting> findById( Long id )
    {
        return repository.findById( id );
    }
    
    
    @Transactional
    @Override
    public Meeting save( Meeting meeting )
    {
        return repository.save( meeting );
    }
    
    
    @Transactional( readOnly = true )
    @Override
    public List<Meeting> findAll()
    {
        return repository.findAll();
    }
}
