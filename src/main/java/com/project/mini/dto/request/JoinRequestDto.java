package com.project.mini.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRequestDto {

    private String username;
    private String nickname;
    private String pw;
    private String pwcheck;
}
