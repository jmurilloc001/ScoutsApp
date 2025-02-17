package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    User save(User user);
    boolean existsByUsername(String username);
}
