package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.Role;
import com.jmurilloc.pfc.scouts.entities.User;
import com.jmurilloc.pfc.scouts.exceptions.RoleNotFoundException;
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

        Optional<Role> roleUserOptional = roleRepository.findByName(RoleNames.USER.getValue());

        if (roleUserOptional.isEmpty()){
            throw new RoleNotFoundException(MessageError.ROLE_NOT_FOUND.getValue());
        }
        Role roleUser = roleUserOptional.get();
        if (!user.getRoles().contains(roleUser)){
            List<Role> roles = new ArrayList<>();
            roles.addAll(user.getRoles());
            roles.add(roleUser);
            user.setRoles(roles);
        }

        if (user.isAdmin()){
            Optional<Role> roleAdminOptional = roleRepository.findByName(RoleNames.ADMIN.getValue());
            if (roleAdminOptional.isEmpty()){
                throw new RoleNotFoundException(MessageError.ROLE_NOT_FOUND.getValue());
            }
            Role roleAdmin = roleAdminOptional.get();
            if (!user.getRoles().contains(roleAdmin)){
                List<Role> roles = new ArrayList<>();
                roles.addAll(user.getRoles());
                roles.add(roleAdmin);
                user.setRoles(roles);
            }
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
}
