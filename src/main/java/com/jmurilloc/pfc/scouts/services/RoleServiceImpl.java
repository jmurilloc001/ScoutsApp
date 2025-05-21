package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.Role;
import com.jmurilloc.pfc.scouts.repositories.RoleRepository;
import com.jmurilloc.pfc.scouts.services.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService
{
    
    private final RoleRepository repository;
    
    
    @Autowired
    public RoleServiceImpl( RoleRepository repository )
    {
        this.repository = repository;
    }
    
    
    @Override
    public Optional<Role> findByName( String name )
    {
        return repository.findByName( name );
    }
    
    
    @Override
    public Optional<Role> findById( Long id )
    {
        return repository.findById( id );
    }
}
