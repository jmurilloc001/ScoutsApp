package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.entities.Affiliate;
import com.jmurilloc.pfc.scouts.entities.Role;
import com.jmurilloc.pfc.scouts.entities.User;
import com.jmurilloc.pfc.scouts.entities.dto.RoleDto;
import com.jmurilloc.pfc.scouts.entities.dto.UserDto;
import com.jmurilloc.pfc.scouts.exceptions.*;
import com.jmurilloc.pfc.scouts.services.AffiliateService;
import com.jmurilloc.pfc.scouts.services.RoleService;
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

import java.util.*;

@CrossOrigin(origins = {"http://localhost:5173"})
@RestController
@RequestMapping("/users")
public class UserController {

    private UserService service;
    private AffiliateService affiliateService;
    private RoleService roleService;

    @Autowired
    public void setService(UserService service) {
        this.service = service;
    }

    @Autowired
    public void setAffiliateService(AffiliateService affiliateService) {
        this.affiliateService = affiliateService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
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

    @GetMapping("/{username}/roles")
    public List<RoleDto> obtenerRoles(@PathVariable String username){
        Optional<User> optionalUser = service.findByUsername(username);
        if (optionalUser.isEmpty()){
            throw new UserNotFoundException(MessageError.USER_NOT_FOUND.getValue());
        }
        User user = optionalUser.get();
        List<RoleDto> roleDtos = new ArrayList<>();
        user.getRoles().forEach(role -> roleDtos.add(BuildDto.buildRoleDto(role)));

        return roleDtos;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{username}")
    public UserDto findUserByUsername(@PathVariable String username){
        Optional<User> optionalUser = service.findByUsername(username);
        if (optionalUser.isEmpty()){
            throw new UserNotFoundException(MessageError.USER_NOT_FOUND.getValue());
        }
        return BuildDto.builUserDto(optionalUser.orElseThrow());
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
        if (result.hasFieldErrors()){
            return UtilValidation.validation(result);
        }
        user.setAdmin(false);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }

    @PreAuthorize("hasAnyRole('ADMIN','COORDI')")
    @PatchMapping("/{userid}/affiliates/{affiliateid}")
    public ResponseEntity<Object> relationUserWithAffiliate(
            @PathVariable Long userid,
            @PathVariable Long affiliateid)
    {
        Optional<User> optionalUser = service.findById(userid);

        if (optionalUser.isPresent()){
            User user = optionalUser.orElseThrow();

            if (user.getAffiliate() != null){//Tiene afiliado, por lo tanto, no se sigue
                throw new UserWithAffiliateException(MessageError.USER_WITH_AFFILIATE.getValue());
            }

            Optional<Affiliate> affiliateOptional = affiliateService.findById(affiliateid);

            if (affiliateOptional.isPresent()){
                Affiliate affiliate = affiliateOptional.orElseThrow();
                if (affiliate.getUser() != null){
                    throw new AffiliateWithUserException(MessageError.AFFILIATE_WITH_USER.getValue());
                }
                user.setAffiliate(affiliate);
            }else {
                throw new AffiliateNotFoundException(MessageError.AFFILIATE_NOT_FOUND.getValue());
            }

            service.save(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(BuildDto.builUserDto(user));
        }
        throw new UserNotFoundException(MessageError.USER_NOT_FOUND.getValue());
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/{id}/password")
    public ResponseEntity<UserDto> changePassword(@PathVariable Long id, @RequestBody Map<String,String> passwordMap){

        String password = passwordMap.get("password");
        if (password == null) {
            throw new BadDataException(MessageError.BAD_FORMAT_JSON.getValue());
        }

        if (password.isBlank() || password.length() < 4 ||password.contains(" ")){
            throw new BadDataException(MessageError.BAD_DATA.getValue());
        }
        User user = service.changePasswordById(password,id);
        if (user == null){
            throw new UserNotFoundException(MessageError.USER_NOT_FOUND.getValue());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(BuildDto.builUserDto(user));


    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/{id}/username")
    public ResponseEntity<Object> changeUsername(@PathVariable Long id, @RequestBody Map<String,String > usernameMap){

        String username = usernameMap.get("username");
        if (username == null) {
            throw new BadDataException(MessageError.BAD_FORMAT_JSON.getValue());
        }

        if (username.isBlank() || username.length() < 4 ||username.length() > 12){
            throw new BadDataException(MessageError.BAD_DATA.getValue());
        }
        User user = service.changeUsernameById(username,id);

        if (user == null){
            throw new UserNotFoundException(MessageError.USER_NOT_FOUND.getValue());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(BuildDto.builUserDto(user));

    }

    @PreAuthorize("hasAnyRole('ADMIN','COORDI')")
    @PatchMapping("/{id}/role/{role}")
    public ResponseEntity<Object> addRole(@PathVariable Long id, @PathVariable String role) {

        role = "ROLE_" + role.toUpperCase().replace(" ", "");

        // Buscar el rol en la base de datos
        Optional<Role> optionalRole = roleService.findByName(role);
        if (optionalRole.isEmpty()) {
            throw new RoleNotFoundException(MessageError.ROLE_NOT_FOUND.getValue());
        }

        Role r = optionalRole.get(); // Obtén el rol

        // Buscar el usuario en la base de datos
        Optional<User> optionalUser = service.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(MessageError.USER_NOT_FOUND.getValue());
        }

        User user = optionalUser.get(); // Obtén el usuario

        // Verificar si el rol ya existe para el usuario
        if (user.getRoles().contains(r)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario ya tiene el rol: " + role);
        }

        service.addRole(user, r);

        return ResponseEntity.ok(BuildDto.builUserDto(user));
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

    @PreAuthorize("hasAnyRole('ADMIN','COORDI','SCOUTER')")
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@Valid @RequestBody User user,BindingResult result,@PathVariable Long id){

        BindingResult newResult = UtilValidation.validateWithoutError(MessageError.VALIDATE_EXISTS_USER_BY_USERNAME.getValue(), result);
        if (newResult != null){
            return UtilValidation.validation(newResult);
        }

        Optional<User> optionalUser = service.findById(id);
        if (optionalUser.isPresent()){
            User u = optionalUser.orElseThrow();

            u.setEnabled(true);

            if (user.getAffiliate() != null){u.setAffiliate(user.getAffiliate());}
            if (user.getRoles() != null){u.setRoles(user.getRoles());}

            u.setPassword(user.getPassword());
            u.setUsername(user.getUsername());

            service.save(u);

            return ResponseEntity.ok(BuildDto.builUserDto(u));
        }

        throw new UserNotFoundException(MessageError.USER_NOT_FOUND.getValue());
    }

    @PreAuthorize("hasAnyRole('ADMIN','COORDI')")
    @DeleteMapping("/{id}/role/{role}")
    public ResponseEntity<Object> deleteRole(@PathVariable Long id, @PathVariable String role){

        role = "ROLE_" + role.toUpperCase().replace(" ", "");

        Optional<Role> optionalRole = roleService.findByName(role);
        if (optionalRole.isEmpty()){
            throw new RoleNotFoundException(MessageError.ROLE_NOT_FOUND.getValue());
        }
        Role r = optionalRole.orElseThrow();

        Optional<User> optionalUser = service.findById(id);
        if (optionalUser.isEmpty()){
            throw new UserNotFoundException(MessageError.USER_NOT_FOUND.getValue());
        }
        User user = optionalUser.orElseThrow();
        service.deleteRole(user,r);
        return ResponseEntity.ok(BuildDto.builUserDto(user));
    }
}
