package com.project.mini.dto.response;

import com.project.mini.models.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

//디테일 페이지 게시글 조회 ResponseDto
@Getter
@NoArgsConstructor
public class PostDetailResponseDto {
    private String nickname;
    private int happypoint;
    private String img;
    private String imgFileName;
    private String content;
    private Long userId;
    private List<CommentResponseDto> comments;


    public PostDetailResponseDto(Post post, List<CommentResponseDto> commentList){
        this.nickname = post.getUser().getNickname();
        this.happypoint = post.getHappypoint();
        this.img = post.getImgUrl();
        this.imgFileName = post.getImgFilename();
        this.content = post.getContent();
        this.comments = commentList;
        this.userId = post.getUser().getId();
    }
}
