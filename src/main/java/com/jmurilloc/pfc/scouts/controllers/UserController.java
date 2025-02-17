package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.entities.Affiliate;
import com.jmurilloc.pfc.scouts.entities.User;
import com.jmurilloc.pfc.scouts.entities.dto.UserDto;
import com.jmurilloc.pfc.scouts.exceptions.AffiliateNotFoundException;
import com.jmurilloc.pfc.scouts.exceptions.UserNotFoundException;
import com.jmurilloc.pfc.scouts.services.AffiliateService;
import com.jmurilloc.pfc.scouts.services.UserService;
import com.jmurilloc.pfc.scouts.util.BuildDto;
import com.jmurilloc.pfc.scouts.util.MessageError;
import com.jmurilloc.pfc.scouts.util.UtilValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/users")
public class UserController {

    private UserService service;
    private AffiliateService affiliateService;

    @Autowired
    public UserController(UserService userService, AffiliateService affiliateService) {
        this.service = userService;
        this.affiliateService = affiliateService;
    }

    @GetMapping
    public List<UserDto> index(){
        List<User> usuarios = service.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for (User usuario : usuarios) {
            userDtoList.add(BuildDto.builUserDto(usuario));
        }
        return userDtoList;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody User user, BindingResult result){
        if (result.hasFieldErrors()){
            return UtilValidation.validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }
    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody User user, BindingResult result){
        user.setAdmin(false);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{userid}/affiliates/{affiliateid}")
    public ResponseEntity<Object> relationUserWithAffiliate(
            @PathVariable Long userid,
            @PathVariable Long affiliateid)
    {
        Optional<User> optionalUser = service.findById(userid);

        if (optionalUser.isPresent()){
            User user = optionalUser.orElseThrow();

            Optional<Affiliate> affiliateOptional = affiliateService.findById(affiliateid);

            if (affiliateOptional.isPresent()){
                Affiliate affiliate = affiliateOptional.orElseThrow();
                user.setAffiliate(affiliate);
            }else {
                throw new AffiliateNotFoundException(MessageError.AFFILIATE_NOT_FOUND.getValue());
            }

            service.save(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(BuildDto.builUserDto(user));
        }
        throw new UserNotFoundException(MessageError.USER_NOT_FOUND.getValue());
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        Optional<User> optionalUser = service.findById(id);
        if (optionalUser.isPresent()){
            User user = optionalUser.orElseThrow();
            service.delete(user);
        }else return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(MessageError.USER_NOT_FOUND.getValue());

        Optional<User> optionalUserPostDelete = service.findById(id);
        if (optionalUserPostDelete.isEmpty()){
            return ResponseEntity.ok("User eliminado correctamente");
        }
        return ResponseEntity.badRequest().body(MessageError.USER_NOT_DELETED.getValue());
    }
}
