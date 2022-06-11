package com.project.mini.controller;

import com.project.mini.dto.JoinRequestDto;
import com.project.mini.dto.LoginRequestDto;
import com.project.mini.security.CustomLogoutSuccessHandler;
import com.project.mini.security.jwt.JwtTokenProvider;
import com.project.mini.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;


    // 회원 로그인

    @PostMapping("/user/login")
    public ResponseEntity login(final HttpServletResponse response, @RequestBody LoginRequestDto loginRequestDto) {
        if (userService.login(loginRequestDto)) {
            String token = jwtTokenProvider.createToken(loginRequestDto.getUsername());
            System.out.println(token);
            response.addHeader("Authorization", token);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } else {
//            response.sendError(100, "닉네임 또는 패스워드를 확인해주세요");
            return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
        }
    }

    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public String registerUser(@Valid @RequestBody JoinRequestDto requestDto) {
        String res = userService.join(requestDto);
        if (res.equals("")) {
            return "회원가입 성공";
        } else {

            return res;
        }
    }

    @GetMapping("/user/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response,
                         Authentication authentication) throws ServletException, IOException {
//        response.addHeader("Authorization", "");
//
//        return "redirect:/";
        customLogoutSuccessHandler.onLogoutSuccess( request, response, authentication);
    }

    @PostMapping("/api/test")
    public String test() {
        System.out.println("테스트");
        return "출력됨";
    }
}
