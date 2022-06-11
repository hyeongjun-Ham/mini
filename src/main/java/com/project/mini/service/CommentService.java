package com.project.mini.service;

import com.project.mini.dto.CommentRequestDto;
import com.project.mini.dto.CommentResponseDto;
import com.project.mini.models.Comment;
import com.project.mini.models.Post;
import com.project.mini.repository.CommentRepository;
import com.project.mini.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public List<Comment> showComment(Long postId) {
        return commentRepository.findAllByPostIdOrderByModifiedAtDesc(postId);
    }

    public CommentResponseDto createComment(Long postId, CommentRequestDto requestDto) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 없습니다.")
        );

        Comment comment = new Comment(post,requestDto);

        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    public void updateComment(Long commentId, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 없습니다.")
        );
        comment.update(requestDto);

    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
