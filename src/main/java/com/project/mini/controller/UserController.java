package com.project.mini.controller;

import com.project.mini.dto.JoinRequestDto;
import com.project.mini.dto.LoginRequestDto;
import com.project.mini.exception.RestApiException;
import com.project.mini.models.User;
import com.project.mini.repository.UserRepository;
import com.project.mini.security.jwt.JwtTokenProvider;
import com.project.mini.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity login(final HttpServletResponse response, @RequestBody LoginRequestDto loginRequestDto) {
        try {
            User user = userRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(null);
            if (userService.login(loginRequestDto)) {
                String token = jwtTokenProvider.createToken(loginRequestDto.getUsername());
                System.out.println(token);
                response.addHeader("Authorization", token);
            }
            Long userId = user.getId();
            return new ResponseEntity(userId, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            RestApiException restApiException = new RestApiException();
            restApiException.setHttpStatus(HttpStatus.BAD_REQUEST);
            restApiException.setErrorMessage(ex.getMessage());
            return new ResponseEntity(restApiException, HttpStatus.BAD_REQUEST);
        }
    }

    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public ResponseEntity registerUser(@Valid @RequestBody JoinRequestDto requestDto) {
        try {
            return new ResponseEntity(userService.join(requestDto), HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            RestApiException restApiException = new RestApiException();
            restApiException.setHttpStatus(HttpStatus.BAD_REQUEST);
            restApiException.setErrorMessage(ex.getMessage());
            return new ResponseEntity(restApiException, HttpStatus.BAD_REQUEST);
        }
    }
}