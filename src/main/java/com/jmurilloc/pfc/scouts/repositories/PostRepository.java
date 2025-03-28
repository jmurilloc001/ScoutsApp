package com.jmurilloc.pfc.scouts.repositories;

import com.jmurilloc.pfc.scouts.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
}
