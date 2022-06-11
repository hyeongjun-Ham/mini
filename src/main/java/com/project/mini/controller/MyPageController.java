package com.project.mini.controller;

import com.project.mini.models.User;
import com.project.mini.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final UserRepository userRepository;

    @GetMapping("/api/mypage")
    public void infoMyPage() {
        User user = userRepository.findById().orElseThrow(
                () -> new IllegalArgumentException("아이디가 없습니다.")
        );
        String nickname = user.getNickname();
        user.getPosts;
    }

    @GetMapping("/api/mypage/ranking")
    public void myRanking() {
        User user = userRepository.findById().orElseThrow(
                () -> new IllegalArgumentException("아이디가 없습니다.")
        );
        user.getHappypoint();

        List<User> all = userRepository.findAll();
        long count = all.stream().count();
    }

}
