package com.project.mini.controller;

import com.project.mini.dto.HappyPointDto;
import com.project.mini.dto.HappyPointResponseDto;
import com.project.mini.dto.JoinRequestDto;
import com.project.mini.dto.LoginRequestDto;
import com.project.mini.models.User;
import com.project.mini.repository.UserRepository;
import com.project.mini.security.CustomLogoutSuccessHandler;
import com.project.mini.security.UserDetailsImpl;
import com.project.mini.security.jwt.JwtTokenProvider;
import com.project.mini.service.UserService;
import jdk.javadoc.internal.doclets.formats.html.Contents;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
//    @GetMapping("/user/logout")
//    public void logout(HttpServletRequest request, HttpServletResponse response,
//                         Authentication authentication) throws ServletException, IOException {
////        response.addHeader("Authorization", "");
//
//        if (authentication != null && authentication.getDetails() != null) {
//            try {
//                request.getHeader("Authentication").replace("");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        response.setStatus(HttpServletResponse.SC_OK);
//        response.sendRedirect("/");
//    }




//    @GetMapping("/user/check")
//    public String userCheck(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        // 로그인 되어 있는 ID의 username
//        String currentUser = userDetails.getUser().getUsername();
//
//        return currentUser;
//    }


//headers.add("Access-Control-Expose-Headers", "token");
//크로스 도

//fetch('http://localhost:8080/login', {
//    method: 'POST',
//    headers: {
//        'Content-Type': 'application/json',
//    },
//    body: JSON.stringify({
//        username: 'linda',
//        password: 'password'
//    })
//}).then(response => {
//    console.log(response.headers.get('Authorization'))
//});