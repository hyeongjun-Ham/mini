package com.project.mini.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRankResponseDto {

    private int avePoint;
    private String nickName;
    private int countPosts;
    private Long createdUser;

}
