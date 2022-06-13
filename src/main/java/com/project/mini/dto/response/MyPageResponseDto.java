package com.project.mini.dto.response;

import com.project.mini.models.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MyPageResponseDto {

    private String nickname;
    private List<Post> posts;
    private int myRank;
    private int totalUser;

}
