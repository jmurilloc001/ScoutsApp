package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.entities.Post;
import com.jmurilloc.pfc.scouts.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    private PostService service;

    @Autowired
    public void setService(PostService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Object> listAllPosts(){
        return ResponseEntity.ok(service.listAllPosts());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }
    @PostMapping
    public ResponseEntity<Object> createPost(@RequestBody Post post){
        return ResponseEntity.ok(service.savePost(post));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable Long id){
        return ResponseEntity.ok(service.deletePost(id));
    }

}
