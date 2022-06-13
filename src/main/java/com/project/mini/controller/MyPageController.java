package com.project.mini.controller;

import com.project.mini.dto.response.MyPageResponseDto;
import com.project.mini.models.Post;
import com.project.mini.models.User;
import com.project.mini.security.UserDetailsImpl;
import com.project.mini.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    //mypage 데이터 전송
    @GetMapping("/api/mypage")
    public MyPageResponseDto infoMyPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        return myPageService.allInfo(userDetails);
    }

}
