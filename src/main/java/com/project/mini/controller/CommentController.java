package com.project.mini.controller;

import com.project.mini.dto.CommentRequestDto;
import com.project.mini.dto.CommentResponseDto;
import com.project.mini.models.Comment;
import com.project.mini.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/api/comments/{postId}")
    public List<Comment> showComment(@PathVariable Long postId) {
        return commentService.showComment(postId);
    }

    @PostMapping("/api/comment/{postId}")
    public CommentResponseDto createComment(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto) {
        return commentService.createComment(postId, requestDto);
    }

    @PutMapping("/api/comment/{commentId}")
    public void updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto) {
        commentService.updateComment(commentId, requestDto);
    }

    @DeleteMapping("/api/comment/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }

}
