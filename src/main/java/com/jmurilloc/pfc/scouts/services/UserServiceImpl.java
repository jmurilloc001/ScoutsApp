package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.Role;
import com.jmurilloc.pfc.scouts.entities.User;
import com.jmurilloc.pfc.scouts.exceptions.RoleNotFoundException;
import com.jmurilloc.pfc.scouts.exceptions.UserWithoutRoleException;
import com.jmurilloc.pfc.scouts.repositories.RoleRepository;
import com.jmurilloc.pfc.scouts.repositories.UserRepository;
import com.jmurilloc.pfc.scouts.util.MessageError;
import com.jmurilloc.pfc.scouts.util.RoleNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository repository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }
    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
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
        Optional<Role> optionalRoleUser = roleRepository.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();

        optionalRoleUser.ifPresent(roles::add);

        if (user.isAdmin()) {
            Optional<Role> optionalRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(roles::add);
        }

        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Override
    @Transactional
    public User addRole(User user, Role role) {
        Optional<Role> optionalRole = roleRepository.findByName(role.getName());
        List<Role> roles = new ArrayList<>();

        if (optionalRole.isEmpty()) {
            throw new RoleNotFoundException("El rol no existe.");
        }
        roles.add(optionalRole.get());

        roles.addAll(user.getRoles());
        user.setRoles(roles);
        return repository.save(user);
    }

    @Transactional
    @Override
    public User deleteRole(User user, Role role) {
        Optional<Role> optionalRole = roleRepository.findByName(role.getName());

        if (optionalRole.isEmpty()) {
            throw new RoleNotFoundException("El rol no existe.");
        }
        List<Role> roles = new ArrayList<>(user.getRoles());
        if (roles.contains(role)){
            roles.remove(optionalRole.get());
        }else {
            throw new UserWithoutRoleException(MessageError.USER_NOT_HAVE_ROLE.getValue());
        }

        user.setRoles(roles);
        return repository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Transactional
    @Override
    public void delete(User user) {
        if (user.getAffiliate() != null){
            user.deleteAffiliate(user.getAffiliate());
        }

        repository.delete(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }
}
