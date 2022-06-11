package com.project.mini.dto;

import com.project.mini.models.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private String comment;

    public CommentResponseDto(Comment comment) {
        this.comment = comment.getComment();
    }
}
