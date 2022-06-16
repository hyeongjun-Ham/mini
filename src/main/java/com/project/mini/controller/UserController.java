package com.project.mini.controller;

import com.project.mini.dto.request.JoinRequestDto;
import com.project.mini.dto.request.LoginRequestDto;
import com.project.mini.dto.response.LoginResponseDto;
import com.project.mini.models.User;
import com.project.mini.repository.UserRepository;
import com.project.mini.security.jwt.JwtTokenProvider;
import com.project.mini.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        User user = userRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디가 없습니다.")
        );

        if (userService.login(loginRequestDto)) {
            String token = jwtTokenProvider.createToken(loginRequestDto.getUsername());
            System.out.println(token);
            response.addHeader("Authorization", token);
        }
        return new LoginResponseDto(user.getId(),user.getNickname());
    }

    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public void registerUser(@Valid @RequestBody JoinRequestDto requestDto) {
        userService.join(requestDto);
    }


    @GetMapping("/user/logout")
    public void logout(final HttpServletResponse response) {
        response.addHeader("Authorization", "");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
