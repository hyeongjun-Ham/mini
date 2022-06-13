package com.project.mini.controller;

import com.project.mini.dto.JoinRequestDto;
import com.project.mini.dto.LoginRequestDto;
import com.project.mini.dto.LoginResponseDto;
import com.project.mini.models.User;
import com.project.mini.repository.UserRepository;
import com.project.mini.security.UserDetailsImpl;
import com.project.mini.security.jwt.JwtTokenProvider;
import com.project.mini.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;


    // 회원 로그인

    @PostMapping("/user/login")
    public LoginResponseDto login(final HttpServletResponse response, @RequestBody LoginRequestDto loginRequestDto) {

        User user = userRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(null);
        if (userService.login(loginRequestDto)) {
            String token = jwtTokenProvider.createToken(loginRequestDto.getUsername());
            System.out.println(token);
            response.addHeader("Authorization", token);
        }
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setUserId(user.getId());
        loginResponseDto.setNickname(user.getNickname());

        return loginResponseDto;
    }

    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public String registerUser(@Valid @RequestBody JoinRequestDto requestDto) {
            return userService.join(requestDto);
    }


    @GetMapping("/user/logout")
    public void logout(final HttpServletResponse response, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        response.addHeader("Authorization", "");
    }

    @ExceptionHandler( IllegalArgumentException.class)
    public ResponseEntity handleException(IllegalArgumentException ex) {

        String errorMessage = ex.getMessage();
        return new ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);
    }

}






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