package com.project.mini.controller;

import com.project.mini.models.Post;
import com.project.mini.models.User;
import com.project.mini.dto.response.UserRankResponseDto;
import com.project.mini.repository.PostRepository;
import com.project.mini.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class MainPageController {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    @GetMapping("/api/postList")
    public List<Post> showPostList() {
        return postRepository.findAll();
    }

    @GetMapping("/api/ranking")
    public List<String> rankingList() {
        List<User> allUser = userRepository.findAll();
        List<UserRankResponseDto> userRankResponseDtos = new ArrayList<>();

        for (int i = 0; i < allUser.size(); i++) {
            int happypoint = allUser.get(i).getHappypoint();
            int countPosts = allUser.get(i).getPosts().size();
            Long createdUser = allUser.get(i).getId();
            String nickname = allUser.get(i).getNickname();

            int avePoint = happypoint / countPosts;
            UserRankResponseDto userRankResponseDto = new UserRankResponseDto(avePoint, nickname, countPosts, createdUser);
            userRankResponseDtos.add(userRankResponseDto);
        }

        userRankResponseDtos.sort(Comparator.comparingInt(UserRankResponseDto::getAvePoint).reversed()
                .thenComparingInt(UserRankResponseDto::getCountPosts).reversed()
                .thenComparingLong(UserRankResponseDto::getCreatedUser)
        );

        List<String> result = new ArrayList<>();
        for (int i = 0; i < 5; i++) {

            result.add(userRankResponseDtos.get(i).getNickName());
        }
        return result;
    }
}
