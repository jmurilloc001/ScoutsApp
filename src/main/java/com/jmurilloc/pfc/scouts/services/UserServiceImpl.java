package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.Role;
import com.jmurilloc.pfc.scouts.entities.User;
import com.jmurilloc.pfc.scouts.repositories.RoleRepository;
import com.jmurilloc.pfc.scouts.repositories.UserRepository;
import com.jmurilloc.pfc.scouts.util.RoleNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository repository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder){
        this.repository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public User save(User user) {
        Optional<Role> roleUser = roleRepository.findByName(RoleNames.USER.getValue());

        roleUser.ifPresent(role -> user.getRoles().add(role));

        if (user.isAdmin()){
            Optional<Role> roleAdmin = roleRepository.findByName(RoleNames.ADMIN.getValue());

            roleAdmin.ifPresent(role -> user.getRoles().add(role));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }
}
