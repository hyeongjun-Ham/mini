package com.project.mini.controller;

import com.project.mini.dto.response.MyPageResponseDto;
import com.project.mini.models.Post;
import com.project.mini.models.User;
import com.project.mini.repository.UserRepository;
import com.project.mini.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final UserRepository userRepository;
    private final MyPageService myPageService;

    @GetMapping("/api/mypage")
    public MyPageResponseDto infoMyPage() {
        User user = userRepository.findById(1L).orElseThrow(
                () -> new IllegalArgumentException("아이디가 없습니다.")
        );

        String nickname = user.getNickname();
        List<Post> posts = user.getPosts();

        int myRank = myPageService.checkMyRank();
        int totalUser = myPageService.checkTotalUser();

        return new MyPageResponseDto(nickname, posts, myRank, totalUser);
    }

}
