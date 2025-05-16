package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.entities.Affiliate;
import com.jmurilloc.pfc.scouts.entities.Role;
import com.jmurilloc.pfc.scouts.entities.User;
import com.jmurilloc.pfc.scouts.entities.dto.RoleDto;
import com.jmurilloc.pfc.scouts.entities.dto.UserDto;
import com.jmurilloc.pfc.scouts.exceptions.AccesDeniedException;
import com.jmurilloc.pfc.scouts.exceptions.AffiliateNotFoundException;
import com.jmurilloc.pfc.scouts.exceptions.AffiliateWithUserException;
import com.jmurilloc.pfc.scouts.exceptions.BadDataException;
import com.jmurilloc.pfc.scouts.exceptions.RoleNotFoundException;
import com.jmurilloc.pfc.scouts.exceptions.UserNotFoundException;
import com.jmurilloc.pfc.scouts.exceptions.UserWithAffiliateException;
import com.jmurilloc.pfc.scouts.exceptions.UserWithoutRoleException;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin( origins = { "http://localhost:5173" } )
@RestController
@RequestMapping( "/users" )
public class UserController
{
    
    private UserService service;
    private AffiliateService affiliateService;
    private RoleService roleService;
    
    
    @Autowired
    public void setService( UserService service )
    {
        this.service = service;
    }
    
    
    @Autowired
    public void setAffiliateService( AffiliateService affiliateService )
    {
        this.affiliateService = affiliateService;
    }
    
    
    @Autowired
    public void setRoleService( RoleService roleService )
    {
        this.roleService = roleService;
    }
    
    
    @GetMapping
    public List<UserDto> index()
    {
        List<User> usuarios = service.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for( User usuario : usuarios )
        {
            userDtoList.add( BuildDto.builDto( usuario ) );
        }
        return userDtoList;
    }
    
    
    @GetMapping( "/{username}/roles" )
    public List<RoleDto> obtenerRoles( @PathVariable String username )
    {
        Optional<User> optionalUser = service.findByUsername( username );
        if( optionalUser.isEmpty() )
        {
            throw new UserNotFoundException( MessageError.USER_NOT_FOUND.getValue() );
        }
        User user = optionalUser.get();
        List<RoleDto> roleDtos = new ArrayList<>();
        user.getRoles().forEach( role -> roleDtos.add( BuildDto.buildDto( role ) ) );
        
        return roleDtos;
    }
    
    
    @PreAuthorize( "hasRole('USER')" )
    @GetMapping( "/{username}" )
    public UserDto findUserByUsername( @PathVariable String username )
    {
        Optional<User> optionalUser = service.findByUsername( username );
        if( optionalUser.isEmpty() )
        {
            throw new UserNotFoundException( MessageError.USER_NOT_FOUND.getValue() );
        }
        return BuildDto.builDto( optionalUser.orElseThrow() );
    }
    
    
    @PreAuthorize( "hasRole('USER')" )
    @GetMapping( "/{username}/affiliateId" )
    public ResponseEntity<Long> getAffiliateIdByUsername( @PathVariable String username )
    {
        Long id = service.getIdAffiliateByUsername( username );
        return ResponseEntity.ok().body( id );
    }
    
    
    @PreAuthorize( "hasRole('ADMIN')" )
    @PostMapping
    public ResponseEntity<Object> save( @Valid @RequestBody User user, BindingResult result )
    {
        if( result.hasFieldErrors() )
        {
            return UtilValidation.validation( result );
        }
        return ResponseEntity.status( HttpStatus.CREATED ).body( service.save( user ) );
    }
    
    
    @PostMapping( "/register" )
    public ResponseEntity<Object> register( @Valid @RequestBody User user, BindingResult result )
    {
        if( result.hasFieldErrors() )
        {
            return UtilValidation.validation( result );
        }
        user.setAdmin( false );
        return ResponseEntity.status( HttpStatus.CREATED ).body( service.save( user ) );
    }
    
    
    @PreAuthorize( "hasRole('USER')" )
    @PatchMapping( "/{userid}/affiliates/{affiliateid}" )
    public ResponseEntity<Object> relationUserWithAffiliate( @PathVariable Long userid, @PathVariable Long affiliateid )
    {
        Optional<User> optionalUser = service.findById( userid );
        
        if( optionalUser.isPresent() )
        {
            User user = optionalUser.orElseThrow();
            
            if( user.getAffiliate() != null )
            {//Tiene afiliado, por lo tanto, no se sigue
                throw new UserWithAffiliateException( MessageError.USER_WITH_AFFILIATE.getValue() );
            }
            
            Optional<Affiliate> affiliateOptional = affiliateService.findById( affiliateid );
            
            Affiliate affiliate;
            if( affiliateOptional.isPresent() )
            {
                affiliate = affiliateOptional.orElseThrow();
                if( affiliate.getUser() != null )
                {
                    throw new AffiliateWithUserException( MessageError.AFFILIATE_WITH_USER.getValue() );
                }
            }
            else
            {
                throw new AffiliateNotFoundException( MessageError.AFFILIATE_NOT_FOUND.getValue() );
            }
            
            return ResponseEntity.status( HttpStatus.CREATED ).body( BuildDto.builDto( service.putAffiliate( user, affiliate ) ) );
        }
        throw new UserNotFoundException( MessageError.USER_NOT_FOUND.getValue() );
    }
    
    
    @PreAuthorize( "hasRole('USER')" )
    @PatchMapping( "/{id}/password" )
    public ResponseEntity<UserDto> changePassword( @PathVariable Long id, @RequestBody Map<String, String> passwordMap, Principal principal )
    {
        
        // Obtener el usuario actual
        Authentication authentication = (Authentication)principal;
        String currentUsername = authentication.getName();
        
        boolean hasAdminRole = comprobarRoleAdmin( authentication );
        
        Optional<User> userOptional = service.findById( id );
        if( userOptional.isEmpty() )
        {
            throw new UserNotFoundException( MessageError.USER_NOT_FOUND.getValue() );
        }
        
        User user = userOptional.orElseThrow();
        
        if( !currentUsername.equals( user.getUsername() ) && !hasAdminRole )
        {
            throw new AccesDeniedException( MessageError.ACCES_DENIED_USER.getValue() );
        }
        
        String password = passwordMap.get( "password" );
        if( password == null )
        {
            throw new BadDataException( MessageError.BAD_FORMAT_JSON.getValue() );
        }
        
        if( password.isBlank() || password.length() < 4 || password.contains( " " ) )
        {
            throw new BadDataException( MessageError.BAD_DATA.getValue() );
        }
        service.changePasswordById( password, user );
        
        return ResponseEntity.status( HttpStatus.CREATED ).body( BuildDto.builDto( user ) );
    }
    
    
    @PreAuthorize( "hasRole('USER')" )
    @PatchMapping( "/{id}/username" )
    public ResponseEntity<Object> changeUsername( @PathVariable Long id, @RequestBody Map<String, String> usernameMap, Principal principal )
    {
        
        String username = usernameMap.get( "username" );
        if( username == null )
        {
            throw new BadDataException( MessageError.BAD_FORMAT_JSON.getValue() );
        }
        
        if( username.isBlank() || username.length() < 4 || username.length() > 12 )
        {
            throw new BadDataException( MessageError.BAD_DATA.getValue() );
        }
        
        // Obtener el usuario actual
        Authentication authentication = (Authentication)principal;
        String currentUsername = authentication.getName();
        
        boolean hasAdminRole = comprobarRoleAdmin( authentication );
        
        Optional<User> userOptional = service.findById( id );
        if( userOptional.isEmpty() )
        {
            throw new UserNotFoundException( MessageError.USER_NOT_FOUND.getValue() );
        }
        
        User user = userOptional.orElseThrow();
        if( !user.getUsername().equals( currentUsername ) && !hasAdminRole )
        {
            throw new AccesDeniedException( MessageError.ACCES_DENIED_USER.getValue() );
        }
        
        service.changeUsernameById( username, user );
        
        return ResponseEntity.status( HttpStatus.CREATED ).body( BuildDto.builDto( user ) );
        
    }
    
    
    @PreAuthorize( "hasAnyRole('ADMIN','COORDI')" )
    @PatchMapping( "/{id}/role/{role}" )
    public ResponseEntity<Object> addRole( @PathVariable Long id, @PathVariable String role )
    {
        
        if( !role.contains( "ROLE" ) )
        {
            role = "ROLE_" + role.toUpperCase().replace( " ", "" );
        }
        
        // Buscar el rol en la base de datos
        Optional<Role> optionalRole = roleService.findByName( role );
        if( optionalRole.isEmpty() )
        {
            throw new RoleNotFoundException( MessageError.ROLE_NOT_FOUND.getValue() );
        }
        
        Role r = optionalRole.get(); // Obtén el rol
        
        // Buscar el usuario en la base de datos
        Optional<User> optionalUser = service.findById( id );
        if( optionalUser.isEmpty() )
        {
            throw new UserNotFoundException( MessageError.USER_NOT_FOUND.getValue() );
        }
        
        User user = optionalUser.get(); // Obtén el usuario
        
        // Verificar si el rol ya existe para el usuario
        if( user.getRoles().contains( r ) )
        {
            return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( "El usuario ya tiene el rol: " + role );
        }
        
        service.addRole( user, r );
        
        return ResponseEntity.status( HttpStatus.CREATED ).body( BuildDto.builDto( user ) );
    }
    
    
    @PreAuthorize( "hasAnyRole('ADMIN','COORDI')" )
    @DeleteMapping( "/{id}/role/{role}" )
    public ResponseEntity<Object> deleteRole( @PathVariable Long id, @PathVariable String role )
    {
        
        if( !role.contains( "ROLE" ) )
        {
            role = "ROLE_" + role.toUpperCase().replace( " ", "" );
        }
        
        // Buscar el rol en la base de datos
        Optional<Role> optionalRole = roleService.findByName( role );
        if( optionalRole.isEmpty() )
        {
            throw new RoleNotFoundException( MessageError.ROLE_NOT_FOUND.getValue() );
        }
        
        Role r = optionalRole.get(); // Obtén el rol
        
        // Buscar el usuario en la base de datos
        Optional<User> optionalUser = service.findById( id );
        if( optionalUser.isEmpty() )
        {
            throw new UserNotFoundException( MessageError.USER_NOT_FOUND.getValue() );
        }
        
        User user = optionalUser.get(); // Obtén el usuario
        
        // Verificar si el rol ya existe para el usuario
        if( user.getRoles().contains( r ) )
        {
            service.deleteRole( user, r );
            return ResponseEntity.status( HttpStatus.CREATED ).body( BuildDto.builDto( user ) );
        }
        throw new UserWithoutRoleException( MessageError.USER_NOT_HAVE_ROLE.getValue() );
    }
    
    
    @PreAuthorize( "hasAnyRole('ADMIN')" )
    @DeleteMapping( "/{id}" )
    public ResponseEntity<String> delete( @PathVariable Long id )
    {
        Optional<User> optionalUser = service.findById( id );
        if( optionalUser.isPresent() )
        {
            User user = optionalUser.orElseThrow();
            service.delete( user );
        }
        else
            return ResponseEntity.status( HttpStatus.NOT_FOUND.value() ).body( MessageError.USER_NOT_FOUND.getValue() );
        
        Optional<User> optionalUserPostDelete = service.findById( id );
        if( optionalUserPostDelete.isEmpty() )
        {
            return ResponseEntity.ok( "User eliminado correctamente" );
        }
        return ResponseEntity.badRequest().body( MessageError.USER_NOT_DELETED.getValue() );
    }
    
    
    @PreAuthorize( "hasAnyRole('ADMIN','COORDI','SCOUTER')" )
    @PutMapping( "/{id}" )
    public ResponseEntity<Object> update( @Valid @RequestBody User user, BindingResult result, @PathVariable Long id )
    {
        
        BindingResult newResult = UtilValidation.validateWithoutError( MessageError.VALIDATE_EXISTS_USER_BY_USERNAME.getValue(), result );
        if( newResult != null )
        {
            return UtilValidation.validation( newResult );
        }
        
        Optional<User> optionalUser = service.findById( id );
        if( optionalUser.isPresent() )
        {
            User u = optionalUser.orElseThrow();
            
            u.setEnabled( true );
            
            if( user.getAffiliate() != null )
            {
                u.setAffiliate( user.getAffiliate() );
            }
            if( user.getRoles() != null )
            {
                u.setRoles( user.getRoles() );
            }
            
            u.setPassword( user.getPassword() );
            u.setUsername( user.getUsername() );
            
            service.save( u );
            
            return ResponseEntity.ok( BuildDto.builDto( u ) );
        }
        
        throw new UserNotFoundException( MessageError.USER_NOT_FOUND.getValue() );
    }
    
    
    private boolean comprobarRoleAdmin( Authentication authentication )
    {
        // Obtener los roles del usuario actual
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream().anyMatch( authority -> authority.getAuthority().equals( "ROLE_ADMIN" ) );
    }
}
