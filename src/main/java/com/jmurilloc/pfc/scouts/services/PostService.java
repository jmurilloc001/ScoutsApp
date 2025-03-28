package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.Post;

import java.util.List;

public interface PostService {

    Post savePost(Post post);
    Post deletePost(Long id);
    Post findById(Long id);
    List<Post> listAllPosts();
    List<String> getPostTypes();
}
