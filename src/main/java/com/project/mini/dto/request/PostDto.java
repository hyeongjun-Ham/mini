package com.project.mini.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostDto {
    private int happypoint;
    private String img;
    private String content;
    public PostDto(int happypoint,String content){
        this.happypoint = happypoint;
        this.content = content;
    }
}
