package com.project.mini.dto.response;

import com.project.mini.models.Post;
import com.project.mini.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

//디테일 페이지 게시글 조회 ResponseDto
@Getter
@NoArgsConstructor
public class PostDetailResponseDto {
    private String nickname;
    private int happypoint;
    private String img;
    private String content;
    private String modifiedAt;
    private String createdAt;

    public PostDetailResponseDto(User user, Post post){
        this.nickname = user.getNickname();
        this.happypoint = user.getHappypoint();
        this.img=post.getImgUrl();
        this.content = post.getContent();
    }
}
