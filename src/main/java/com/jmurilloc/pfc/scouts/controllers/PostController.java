package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.entities.Post;
import com.jmurilloc.pfc.scouts.services.interfaces.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/posts" )
public class PostController
{
    
    private PostService service;
    
    
    @Autowired
    public void setService( PostService service )
    {
        this.service = service;
    }
    
    
    @PreAuthorize( "hasAnyRole('ADMIN','USER')" )
    @GetMapping
    public ResponseEntity<Object> listAllPosts()
    {
        return ResponseEntity.ok( service.listAllPosts() );
    }
    
    
    @PreAuthorize( "hasRole('USER')" )
    @GetMapping( "/{id}" )
    public ResponseEntity<Object> findById( @PathVariable Long id )
    {
        return ResponseEntity.ok( service.findById( id ) );
    }
    
    
    @PreAuthorize( "hasRole('USER')" )
    @PostMapping
    public ResponseEntity<Object> createPost( @RequestBody Post post )
    {
        return ResponseEntity.status( HttpStatus.CREATED ).body( service.savePost( post ) );
    }
    
    
    @PreAuthorize( "hasRole('USER')" )
    @DeleteMapping( "/{id}" )
    public ResponseEntity<Object> deletePost( @PathVariable Long id )
    {
        return ResponseEntity.ok( service.deletePost( id ) );
    }
    
    
    @PreAuthorize( "hasRole('USER')" )
    @PutMapping( "/{id}" )
    public ResponseEntity<Object> updatePost( @PathVariable Long id, @RequestBody Post post )
    {
        post.setId( id );
        return ResponseEntity.ok( service.updatePost( post ) );
    }
    
}
