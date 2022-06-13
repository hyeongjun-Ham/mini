package com.project.mini.controller;

import com.project.mini.dto.JoinRequestDto;
import com.project.mini.dto.LoginRequestDto;
import com.project.mini.security.CustomLogoutSuccessHandler;
import com.project.mini.security.jwt.JwtTokenProvider;
import com.project.mini.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;


    // 회원 로그인

    @PostMapping("/user/login")
    public String login(final HttpServletResponse response, @RequestBody LoginRequestDto loginRequestDto) {
        if (userService.login(loginRequestDto)) {
            String token = jwtTokenProvider.createToken(loginRequestDto.getUsername());
            System.out.println(token);
            response.addHeader("Authorization", token);
            return "로그인 성공";
        }
        return "로그인 실패";
    }

    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public String registerUser(@Valid @RequestBody JoinRequestDto requestDto) {
        String res = userService.join(requestDto);
        if (res.equals("")) {
            return "회원가입 성공";
        } else {
            return "회원가입 실패";
        }
    }
}