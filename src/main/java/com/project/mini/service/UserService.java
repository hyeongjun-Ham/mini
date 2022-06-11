package com.project.mini.service;

import com.project.mini.models.User;
import com.project.mini.dto.JoinRequestDto;
import com.project.mini.dto.LoginRequestDto;
import com.project.mini.security.jwt.JwtTokenProvider;
import com.project.mini.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";
    private final AuthenticationManager authenticationManager;

    //회원가입
    public String join(JoinRequestDto requestDto) {
        String error = "";
        String username = requestDto.getUsername();
        String nickname = requestDto.getNickname();
        String pw = requestDto.getPw();
        String pwcheck = requestDto.getPwcheck();
        String pattern = "^[a-zA-Z0-9]*$";

        // 회원 ID 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            return "중복된 id 입니다.";
        }

        // 회원 닉네임 중복 확인
        Optional<User> foundUserNickname = userRepository.findByNickname(requestDto.getNickname());
        if (foundUserNickname.isPresent()) {
            throw new IllegalArgumentException("사용자 닉네임이 이미 존재합니다.");
        }

        // 회원가입 조건
        if (username.length() < 3) {
            return "닉네임을 3자 이상 입력하세요";
        } else if (!Pattern.matches(pattern, username)) {
            return "알파벳 대소문자와 숫자로만 입력하세요";
        } else if (!pw.equals(pwcheck)) {
            return "비밀번호가 일치하지 않습니다";
        } else if (pw.length() < 4) {
            return "비밀번호를 4자 이상 입력하세요";
        } else if (pw.contains(username)) {
            return "비밀번호에 닉네임을 포함할 수 없습니다.";
        }

        // 패스워드 인코딩
        pw = passwordEncoder.encode(pw);
        requestDto.setPw(pw);

        // 유저 정보 저장
        User user = new User(username, nickname, pw);
        userRepository.save(user);
        return error;
    }

    // 로그인
    public Boolean login(LoginRequestDto loginRequestDto){
        User user = userRepository.findByUsername(loginRequestDto.getUsername())
                .orElse(null);
        if (user != null) {
            if (!passwordEncoder.matches(loginRequestDto.getPw(), user.getPw())) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }


}
