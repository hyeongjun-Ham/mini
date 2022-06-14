package com.project.mini.controller;

import com.project.mini.dto.response.MyPageResponseDto;
import com.project.mini.security.UserDetailsImpl;
import com.project.mini.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    //mypage 데이터 전송
    @GetMapping("/api/mypage")
    public MyPageResponseDto infoMyPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        return myPageService.allInfo(userDetails);
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleException(NullPointerException e) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("로그인 해주세요");
    }
}
