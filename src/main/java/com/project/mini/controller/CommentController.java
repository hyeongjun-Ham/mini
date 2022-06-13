package com.project.mini.controller;

import com.project.mini.dto.CommentRequestDto;
import com.project.mini.dto.CommentResponseDto;
import com.project.mini.security.UserDetailsImpl;
import com.project.mini.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //게시글의 댓글 보기
    @GetMapping("/api/comments/{postId}")
    public List<CommentResponseDto> showComment(@PathVariable Long postId) {
        return commentService.showComment(postId);
    }

    //댓글 작성
    @PostMapping("/api/comment/{postId}")
    public void createComment(@PathVariable Long postId,
                              @RequestBody CommentRequestDto requestDto,
                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.createComment(postId, requestDto, userDetails);
    }

    @PutMapping("/api/comment/{commentId}")
    public void updateComment(@PathVariable Long commentId,
                              @RequestBody CommentRequestDto requestDto,
                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.updateComment(commentId, requestDto, userDetails);
    }

    @DeleteMapping("/api/comment/{commentId}")
    public void deleteComment(@PathVariable Long commentId,
                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(commentId, userDetails);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("로그인을 확인해 주세요.");
    }
}
