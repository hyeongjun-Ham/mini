package com.project.mini.service;

import com.project.mini.dto.CommentRequestDto;
import com.project.mini.dto.CommentResponseDto;
import com.project.mini.models.Comment;
import com.project.mini.models.Post;
import com.project.mini.models.User;
import com.project.mini.repository.CommentRepository;
import com.project.mini.repository.PostRepository;
import com.project.mini.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public List<CommentResponseDto> showComment(Long postId) {
        List<Comment> commentList = commentRepository.findAllByPost_PostId(postId);

        List<CommentResponseDto> list = new ArrayList<>();
        for (Comment comment : commentList ) {

            CommentResponseDto commentResponseDto = new CommentResponseDto();
            commentResponseDto.setUserId(comment.getUser().getId());
            commentResponseDto.setNickname(comment.getUser().getNickname());
            commentResponseDto.setComment(comment.getComment());
            commentResponseDto.setCommentId(comment.getCommentId());

            list.add(commentResponseDto);
        }

        return list;

    }

    public CommentResponseDto createComment(Long postId, CommentRequestDto requestDto, UserDetailsImpl userDetails) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 없습니다.")
        );
        User user = userDetails.getUser();

        Comment comment = new Comment(post,requestDto,user);

        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    public void updateComment(Long commentId, CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 없습니다.")
        );
        Long nowUserId = userDetails.getUser().getId();
        Long dbId = comment.getUser().getId();

        if (nowUserId.equals(dbId)) {
            comment.update(requestDto);
            commentRepository.save(comment);
        }
    }

    public void deleteComment(Long commentId, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 없습니다.")
        );
        Long nowUserId = userDetails.getUser().getId();
        Long dbId = comment.getUser().getId();

        if (nowUserId.equals(dbId)) {
            commentRepository.deleteById(commentId);
        }
    }
}
