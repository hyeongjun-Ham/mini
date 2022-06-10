package com.project.mini.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JoinRequestDto {

    private String username;
    private String nickname;
    private String pw;
    private String pwcheck;
}
