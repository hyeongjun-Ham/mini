package com.project.mini.service;

import com.project.mini.dto.request.JoinRequestDto;
import com.project.mini.dto.request.LoginRequestDto;
import com.project.mini.models.User;
import com.project.mini.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    public void join(JoinRequestDto requestDto) {
        String username = requestDto.getUsername();
        String nickname = requestDto.getNickname();
        String pw = requestDto.getPw();
        String pwcheck = requestDto.getPwcheck();
        String pattern = "^[a-zA-Z0-9]*$";

        // 회원 ID 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 ID 입니다.");
        }

        // 회원 닉네임 중복 확인
        Optional<User> foundUserNickname = userRepository.findByNickname(nickname);
        if (foundUserNickname.isPresent()) {
            throw new IllegalArgumentException("사용자 닉네임이 이미 존재합니다.");
        }

        // 회원가입 조건
        if (username.length() < 3) {
            throw new IllegalArgumentException("아이디를 3자 이상 입력하세요");
        } else if (!Pattern.matches(pattern, username)) {
            throw new IllegalArgumentException("알파벳 대소문자와 숫자로만 입력하세요");
        } else if (!pw.equals(pwcheck)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        } else if (pw.length() < 4) {
            throw new IllegalArgumentException("비밀번호를 4자 이상 입력하세요");
        } else if (pw.contains(username)) {
            throw new IllegalArgumentException("비밀번호에 닉네임을 포함할 수 없습니다.");
        }

        // 패스워드 인코딩
        pw = passwordEncoder.encode(pw);
        requestDto.setPw(pw);

        // 유저 정보 저장
        User user = new User(username, nickname, pw);
        userRepository.save(user);
    }

    // 로그인
    public Boolean login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByUsername(loginRequestDto.getUsername())
                .orElse(null);
        if (user != null) {
            if (!passwordEncoder.matches(loginRequestDto.getPw(), user.getPw())) {
                throw new IllegalArgumentException("아이디 혹은 비밀번호가 다릅니다.");
            }
        } else {
            throw new IllegalArgumentException("아이디 혹은 비밀번호가 다릅니다.");
        }
        return true;
    }
}
