package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.Affiliate;
import com.jmurilloc.pfc.scouts.repositories.AffiliatesRepository;
import com.jmurilloc.pfc.scouts.services.interfaces.AffiliateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AffiliateServiceImpl implements AffiliateService
{
    
    private AffiliatesRepository repository;
    
    
    @Autowired
    public void setRepository( AffiliatesRepository repository )
    {
        this.repository = repository;
    }
    
    
    @Transactional( readOnly = true )
    @Override
    public Optional<Affiliate> findById( Long id )
    {
        return repository.findById( id );
    }
    
    
    @Transactional
    @Override
    public Affiliate save( Affiliate affiliate )
    {
        return repository.save( affiliate );
    }
    
    
    @Transactional( readOnly = true )
    @Override
    public List<Affiliate> findAll()
    {
        return repository.findAll();
    }
    
    
    @Transactional
    @Override
    public void delete( Affiliate affiliate )
    {
        if( affiliate.getUser() != null )
        {
            affiliate.deleteUser( affiliate.getUser() );
        }
        
        repository.delete( affiliate );
    }
    
    
    @Override
    @Transactional( readOnly = true )
    public Optional<Affiliate> findByName( String name )
    {
        return repository.findByName( name );
    }
    
}
