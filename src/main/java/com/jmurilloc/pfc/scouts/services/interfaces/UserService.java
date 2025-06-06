package com.jmurilloc.pfc.scouts.services.interfaces;

import com.jmurilloc.pfc.scouts.entities.Affiliate;
import com.jmurilloc.pfc.scouts.entities.Role;
import com.jmurilloc.pfc.scouts.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService
{
    
    List<User> findAll();
    
    
    Optional<User> findById( Long id );
    
    
    User save( User user );
    
    
    boolean existsByUsername( String username );
    
    
    void delete( User user );
    
    
    Optional<User> findByUsername( String username );
    
    
    User addRole( User user, Role role );
    
    
    User deleteRole( User user, Role role );
    
    
    User changeUsernameById( String username, User user );
    
    
    User changePasswordById( String password, User user );
    
    
    User putAffiliate( User user, Affiliate affiliate );
    
    
    Long getIdAffiliateByUsername( String username );
}
