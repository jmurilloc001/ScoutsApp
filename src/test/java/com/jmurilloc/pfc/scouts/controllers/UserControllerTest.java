package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.entities.Affiliate;
import com.jmurilloc.pfc.scouts.entities.Role;
import com.jmurilloc.pfc.scouts.entities.User;
import com.jmurilloc.pfc.scouts.exceptions.AffiliateNotFoundException;
import com.jmurilloc.pfc.scouts.exceptions.BadDataException;
import com.jmurilloc.pfc.scouts.exceptions.RoleNotFoundException;
import com.jmurilloc.pfc.scouts.exceptions.UserNotFoundException;
import com.jmurilloc.pfc.scouts.exceptions.UserWithAffiliateException;
import com.jmurilloc.pfc.scouts.services.AffiliateService;
import com.jmurilloc.pfc.scouts.services.RoleService;
import com.jmurilloc.pfc.scouts.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith( MockitoExtension.class )
class UserControllerTest
{
    @InjectMocks
    private UserController userController;
    
    @Mock
    private UserService userService;
    
    @Mock
    private AffiliateService affiliateService;
    
    @Mock
    private RoleService roleService;
    
    
    @Test
    void indexTest(){
        Mockito.when( userService.findAll() ).thenReturn( new ArrayList<User>() );
        assertEquals( 0, userController.index().size() );
    }
    
    @Test
    void obtenerRolesTest(){
        User user = new User();
        user.setRoles( Arrays.asList( new Role("test1"),new Role("test2") ) );
        Optional<User> optionalUser = Optional.of( user );
        Mockito.when( userService.findByUsername( Mockito.anyString() ) ).thenReturn( optionalUser );
        assertEquals(2, userController.obtenerRoles( "test" ).size());
    }
    @Test
    void obtenerRolesErrorEmptyTest(){
        Optional<User> optionalUser = Optional.empty();
        Mockito.when( userService.findByUsername( Mockito.anyString() ) ).thenReturn( optionalUser );
        assertThrows( UserNotFoundException.class, () -> userController.obtenerRoles( "test" ) );
    }
    
    @Test
    void findByUsernameTest(){
        User user = new User();
        user.setUsername( "Test" );
        Mockito.when( userService.findByUsername( Mockito.anyString() ) ).thenReturn( Optional.of( user ) );
        assertEquals( "Test", userController.findUserByUsername( "Test" ).getUsername() );
    }
    
    @Test
    void findByUsernameErrorEmptyTest(){
        Mockito.when( userService.findByUsername( Mockito.anyString() ) ).thenReturn( Optional.empty() );
        assertThrows( UserNotFoundException.class, () -> userController.findUserByUsername( "Test" ) );
    }
    
    @Test
    void getAffiliateIdByUsernameTest(){
        Mockito.when( userService.getIdAffiliateByUsername( "Test" ) ).thenReturn( 1L );
        assertEquals( ResponseEntity.ok(1L), userController.getAffiliateIdByUsername( "Test" ));
    }
    
    @Test
    void saveTest(){
        User user = new User();
        user.setUsername( "Test" );
        Mockito.when( userService.save( Mockito.any() ) ).thenReturn( user );
        assertEquals( ResponseEntity.status( HttpStatus.CREATED ).body( user ), userController.save( user, Mockito.mock( BindingResult.class )));
    }
    
    @Test
    void registerTest(){
        User user = new User();
        user.setUsername( "Test" );
        user.setAdmin( true );
        Mockito.when( userService.save( Mockito.any() ) ).thenReturn( user );
        ResponseEntity<Object> test = userController.register( user, Mockito.mock( BindingResult.class ) );
        assertEquals( ResponseEntity.status( HttpStatus.CREATED ).body( user ), test );
        assertNotNull( test.getBody() );
        System.out.println( ((User) test.getBody()).isAdmin() );
        assertEquals( false,((User )test.getBody()).isAdmin()  );
    }
    
    @Test
    void relationUserWithAffiliateTest(){
        User user = new User();
        Affiliate affiliate = new Affiliate();
        affiliate.setId( 1L );
        Mockito.when( userService.findById( 1L )).thenReturn( Optional.of( user ) );
        Mockito.when( affiliateService.findById( Mockito.anyLong() ) ).thenReturn( Optional.of( affiliate ) );
        Mockito.when( userService.putAffiliate( Mockito.any(),Mockito.any() ) ).thenReturn( user );
        
        userController.relationUserWithAffiliate( 1L, 1L );
        
        verify( userService ).putAffiliate( any( User.class), any( Affiliate.class ) );
    }
    
    @Test
    void relationUserWithAffiliateUserNotFoundTest(){
        Mockito.when( userService.findById( 1L )).thenReturn( Optional.empty() );
        assertThrows( UserNotFoundException.class, () -> userController.relationUserWithAffiliate( 1L, 1L ) );
    }
    @Test
    void relationUserWithAffiliateAffiliateNotFoundTest(){
        User user = new User();
        Mockito.when( userService.findById( 1L )).thenReturn( Optional.of( user ) );
        Mockito.when( affiliateService.findById( Mockito.anyLong() ) ).thenReturn( Optional.empty() );
        assertThrows( AffiliateNotFoundException.class, () -> userController.relationUserWithAffiliate( 1L, 1L ) );
    }
    @Test
    void relationUserWithAffiliateUserWithAffiliateTest(){
        User user = new User();
        Affiliate affiliate = new Affiliate();
        user.setAffiliate( affiliate );
        Mockito.when( userService.findById( 1L )).thenReturn( Optional.of( user ) );
        assertThrows( UserWithAffiliateException.class, () -> userController.relationUserWithAffiliate( 1L, 1L ) );
    }
    @Test
    void changeUsernameTest(){
        Authentication authentication = Mockito.mock( Authentication.class );
        Mockito.when( authentication.getName() ).thenReturn( "currentUsername" );
        
        Principal principal = (Principal) authentication;
        Long userId = 1L;
        String newUsername = "newUsername";
        Map<String,String> usernameMap = Map.of( "username", newUsername );
        
        User user = new User();
        user.setId( userId );
        user.setUsername( "currentUsername" );
        
        Mockito.when( userService.findById( userId ) ).thenReturn( Optional.of( user ) );
        
        ResponseEntity<Object> response = userController.changeUsername( userId,usernameMap,principal );
        assertEquals( HttpStatus.CREATED,response.getStatusCode() );
        verify( userService ).changeUsernameById( newUsername,user );
    }
    @Test
    void changeUsernameJsonBadFormatTest(){
        Authentication authentication = Mockito.mock( Authentication.class );
        
        Principal principal = (Principal) authentication;
        
        Long userId = 1L;
        Map<String,String> usernameMap = Map.of( "test fallo", "newUsername" );
        
        assertThrows( BadDataException.class, () -> userController.changeUsername( userId,usernameMap,principal ) );
    }
    @Test
    void changeUsernameBadDataErrorTest(){
        Authentication authentication = Mockito.mock( Authentication.class );
        
        Principal principal = (Principal) authentication;
        Long userId = 1L;
        String newUsername = "";
        Map<String,String> usernameMap = Map.of( "username", newUsername );
        
        assertThrows( BadDataException.class, () -> userController.changeUsername( userId,usernameMap,principal ) );
    }
    
    @Test
    void addRoleTest() {
        Long userId = 1L;
        String roleName = "ROLE_TEST";
        
        Role role = new Role();
        role.setName(roleName);
        
        User user = new User();
        user.setId(userId);
        user.setRoles(new ArrayList<>());
        
        Mockito.when(roleService.findByName(roleName)).thenReturn(Optional.of(role));
        Mockito.when(userService.findById(userId)).thenReturn(Optional.of(user));
        
        ResponseEntity<Object> response = userController.addRole(userId, roleName);
        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userService,times( 1 )).addRole(user, role);
    }
    
    @Test
    void addRoleEmptyRoleErrorTest(){
        Long userId = 1L;
        String roleName = "ROLE_TEST";
        
        User user = new User();
        user.setId(userId);
        user.setRoles(new ArrayList<>());
        
        Mockito.when(roleService.findByName(roleName)).thenReturn(Optional.empty());
        
        assertThrows( RoleNotFoundException.class, () -> userController.addRole(userId, roleName) );
    }
    
    @Test
    void addRoleUserContainRoleErrorTest(){
        Long userId = 1L;
        String roleName = "ROLE_TEST";
        
        User user = new User();
        user.setId(userId);
        user.setRoles(Arrays.asList( new Role(roleName)));
        
        Mockito.when(roleService.findByName(roleName)).thenReturn(Optional.of(new Role(roleName)));
        Mockito.when( userService.findById( 1L ) ).thenReturn( Optional.of( user ) );
        assertEquals( HttpStatus.BAD_REQUEST, userController.addRole( userId, roleName ).getStatusCode() );
    }
    @Test
    void deleteRoleTest() {
        Long userId = 1L;
        String roleName = "ROLE_TEST";
        
        Role role = new Role();
        role.setName(roleName);
        
        User user = new User();
        user.setId(userId);
        user.setRoles(Arrays.asList(role));
        
        Mockito.when(roleService.findByName(roleName)).thenReturn(Optional.of(role));
        Mockito.when(userService.findById(userId)).thenReturn(Optional.of(user));
        
        ResponseEntity<Object> response = userController.deleteRole(userId, roleName);
        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userService, times(1)).deleteRole(user, role);
    }
    @Test
    void deleteRoleNotFoundRoleErrorTest() {
        Long userId = 1L;
        String roleName = "ROLE_TEST";
        
        Role role = new Role();
        role.setName(roleName);
        
        Mockito.when(roleService.findByName(roleName)).thenReturn(Optional.empty());
        
        assertThrows( RoleNotFoundException.class, () -> userController.deleteRole( userId, roleName ) );
    }
    @Test
    void deleteTest(){
        Long userId = 1L;
        
        User user = new User();
        user.setId(userId);
        
        // Mockear la búsqueda del usuario antes de eliminar
        Mockito.when(userService.findById(userId)).thenReturn(Optional.of(user)).thenReturn(Optional.empty());
        
        ResponseEntity<String> response = userController.delete(userId);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User eliminado correctamente", response.getBody());
        
        verify(userService, times(1)).delete(user);
    }
    
    @Test
    void updateTest() {
        Long userId = 1L;
        
        User user = new User();
        user.setId(userId);
        user.setUsername("updatedUser");
        
        User existingUser = new User();
        existingUser.setId(userId);
        
        Mockito.when(userService.findById(userId)).thenReturn(Optional.of(existingUser));
        Mockito.when(userService.save(any(User.class))).thenReturn(user);
        
        ResponseEntity<Object> response = userController.update(user, Mockito.mock(BindingResult.class), userId);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService).save(existingUser);
    }
    
}