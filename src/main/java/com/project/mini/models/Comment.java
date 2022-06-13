package com.project.mini.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.mini.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn
    @JsonIgnore //무한루프 끊기
    private Post post;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private User user;



    public Comment(Post post ,CommentRequestDto requestDto, User user) {
        this.post = post;
        this.comment = requestDto.getComment();
        this.user = user;
    }

    public void update(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }
}
