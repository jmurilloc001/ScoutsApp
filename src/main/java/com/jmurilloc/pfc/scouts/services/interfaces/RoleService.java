package com.jmurilloc.pfc.scouts.services.interfaces;

import com.jmurilloc.pfc.scouts.entities.Role;

import java.util.Optional;

public interface RoleService
{
    
    Optional<Role> findByName( String name );
    
    
    Optional<Role> findById( Long id );
}
