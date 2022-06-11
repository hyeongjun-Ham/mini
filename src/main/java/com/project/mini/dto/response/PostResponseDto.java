package com.project.mini.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDto {
    private boolean ok;
    private String message;

    public PostResponseDto(boolean ok, String message){
        this.ok = ok;
        this.message= message;
    }
}
