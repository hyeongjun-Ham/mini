package com.project.mini.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MainPageResponseDto {
    private Long postId;
    private String nickname;
    private String img;
    private String content;
    private int happypoint;

}