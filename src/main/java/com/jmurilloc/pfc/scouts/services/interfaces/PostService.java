package com.jmurilloc.pfc.scouts.services.interfaces;

import com.jmurilloc.pfc.scouts.entities.Post;
import com.jmurilloc.pfc.scouts.entities.dto.PostDto;

import java.util.List;

public interface PostService
{
    
    PostDto savePost( Post post );
    
    
    PostDto deletePost( Long id );
    
    
    PostDto findById( Long id );
    
    
    List<PostDto> listAllPosts();
    
    
    List<String> getPostTypes();
    
    
    PostDto updatePost( Post post );
}
