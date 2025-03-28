package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.Post;
import com.jmurilloc.pfc.scouts.exceptions.PostNotCouldntCreateException;
import com.jmurilloc.pfc.scouts.exceptions.PostNotFoundException;
import com.jmurilloc.pfc.scouts.repositories.PostRepository;
import com.jmurilloc.pfc.scouts.util.MessageError;
import com.jmurilloc.pfc.scouts.util.PostType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostServiceImpl implements PostService{

    private PostRepository repository;

    @Autowired
    public void setRepository(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public Post savePost(Post post) {
        if (post.getAffiliate() == null){
            throw new PostNotCouldntCreateException(MessageError.POST_NEED_AFFILIATE.getValue());
        }
        if (post.getDescription() == null || post.getDescription().isEmpty()){
            throw new PostNotCouldntCreateException(MessageError.POST_NEED_DESCRIPTION.getValue());
        }
        if (post.getType() == null) {
            throw new PostNotCouldntCreateException(MessageError.POST_NEED_TYPE.getValue());
        }
        return repository.save(post);
    }

    @Override
    public Post deletePost(Long id) {
        Post post = findById(id);
        repository.delete(post);
        return post;
    }

    @Override
    public Post findById(Long id) {
        Optional<Post> postOptional = repository.findById(id);
        if (postOptional.isEmpty())throw new PostNotFoundException(MessageError.POST_NOT_FOUND.getValue());
        return postOptional.get();
    }

    @Override
    public List<Post> listAllPosts() {
        List<Post> posts = repository.findAll();
        if (posts.isEmpty()) throw new PostNotFoundException(MessageError.POST_NOT_FOUND.getValue());
        return posts;
    }

    @Override
    public List<String> getPostTypes() {
        List<String> postTypes = new ArrayList<>();
        for (PostType postType : PostType.values()){
            postTypes.add(postType.name());
        }
        if (postTypes.isEmpty()) throw new PostNotFoundException("No se han encontrado tipos de post");
        return postTypes;
    }
}
