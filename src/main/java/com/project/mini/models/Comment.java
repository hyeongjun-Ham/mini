package com.project.mini.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.mini.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String comment;

    @ManyToOne
    @JoinColumn
    @JsonIgnore //무한루프 끊기
    private Post post;


    public Comment(Post post ,CommentRequestDto requestDto) {
        this.post = post;
        this.comment = requestDto.getComment();
    }

    public void update(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }
}
