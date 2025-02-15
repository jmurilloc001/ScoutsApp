package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.entities.User;
import com.jmurilloc.pfc.scouts.entities.dto.UserDto;
import com.jmurilloc.pfc.scouts.services.UserService;
import com.jmurilloc.pfc.scouts.util.UtilValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService service;

    @Autowired
    public UserController(UserService userService) {
        this.service = userService;
    }

    @GetMapping
    public List<UserDto> index(){
        List<User> usuarios = service.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for (User usuario : usuarios) {
            userDtoList.add(new UserDto(usuario.getUsername(),usuario.getAffiliate().getName(),usuario.getAffiliate().getLastname(),usuario.getRoles(),usuario.isEnabled()));
        }
        return userDtoList;
    }

    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody User user, BindingResult result){
        if (result.hasFieldErrors()){
            return UtilValidation.validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }


}
