package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.Post;
import com.jmurilloc.pfc.scouts.entities.dto.PostDto;
import com.jmurilloc.pfc.scouts.exceptions.BadDataException;
import com.jmurilloc.pfc.scouts.exceptions.PostCouldntCreateException;
import com.jmurilloc.pfc.scouts.exceptions.PostNotFoundException;
import com.jmurilloc.pfc.scouts.repositories.PostRepository;
import com.jmurilloc.pfc.scouts.util.BuildDto;
import com.jmurilloc.pfc.scouts.util.BuildEntity;
import com.jmurilloc.pfc.scouts.util.MessageError;
import com.jmurilloc.pfc.scouts.util.PostType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{

    private PostRepository repository;

    @Autowired
    public void setRepository(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public PostDto savePost(Post post) {
        if (post.getAffiliate() == null){
            throw new PostCouldntCreateException(MessageError.POST_NEED_AFFILIATE.getValue());
        }
        if (post.getDescription() == null || post.getDescription().isEmpty()){
            throw new PostCouldntCreateException(MessageError.POST_NEED_DESCRIPTION.getValue());
        }
        if (post.getType() == null) {
            throw new PostCouldntCreateException(MessageError.POST_NEED_TYPE.getValue());
        }
        return BuildDto.buildPostDto(repository.save(post));
    }

    @Override
    public PostDto deletePost(Long id) {
        PostDto post = findById(id);
        repository.delete(BuildEntity.buildPost(post));
        return post;
    }

    @Override
    public PostDto findById(Long id) {
        Optional<Post> postOptional = repository.findById(id);
        if (postOptional.isEmpty())throw new PostNotFoundException(MessageError.POST_NOT_FOUND.getValue());
        return BuildDto.buildPostDto(postOptional.get());
    }

    @Override
    public List<PostDto> listAllPosts() {
        List<Post> posts = repository.findAll();
        List<PostDto> listOfPostDto = new ArrayList<>();
        for (Post post : posts){
            listOfPostDto.add(BuildDto.buildPostDto(post));
        }
        if (listOfPostDto.isEmpty()) throw new PostNotFoundException(MessageError.POST_NOT_FOUND.getValue());
        return listOfPostDto;
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

    @Override
    public PostDto updatePost(Post post) {
        if (post.getId() != null){
            findById(post.getId());
            if (post.getDescription().isEmpty()) throw new BadDataException(MessageError.POST_NEED_DESCRIPTION.getValue());
            if (post.getType() == null) throw new BadDataException(MessageError.POST_NEED_TYPE.getValue());
            if (post.getAffiliate() == null) throw new BadDataException(MessageError.POST_NEED_AFFILIATE.getValue());
            return BuildDto.buildPostDto(repository.save(post));
        }
        throw new PostCouldntCreateException(MessageError.POST_NEED_ID.getValue());
    }

}
