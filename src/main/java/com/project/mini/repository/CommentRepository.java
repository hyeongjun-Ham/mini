package com.project.mini.repository;

import com.project.mini.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("Select this_ from Comment this_ where this_.post.postId=:id order by this_.modifiedAt desc")
    List<Comment> findAllByPostIdOrderByModifiedAtDesc(@Param("id") Long postId);

//    List<Comment> findAllByPostIdOrderByModifiedAtDesc(Long postId);
}
