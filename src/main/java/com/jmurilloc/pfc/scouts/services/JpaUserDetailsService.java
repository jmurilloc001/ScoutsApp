package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.User;
import com.jmurilloc.pfc.scouts.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private UserRepository repository;

    @Autowired
    public JpaUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) { //Esto es como el login de SpringSecurity
        Optional<User> userOptional = repository.findByUsername(username);
        if (userOptional.isEmpty()){
            throw new UsernameNotFoundException("Username " + username + " no existe en el sistema");
        }
        User user = userOptional.get();
        Set<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                . collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),user.isEnabled(),true,true,true,authorities);
    }
}
