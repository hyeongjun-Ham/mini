package com.project.mini.dto.response;

import com.project.mini.models.Comment;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDto {

    private Long commentId;
    private String comment;

    private String nickname;

    private Long userId;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.comment = comment.getComment();
        this.userId = comment.getUser().getId();
        this.nickname = comment.getUser().getNickname();
    }

}
