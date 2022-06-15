package com.project.mini.service;

import com.project.mini.dto.response.MainPageResponseDto;
import com.project.mini.dto.response.RankingResponseDto;
import com.project.mini.dto.response.UserRankResponseDto;
import com.project.mini.models.Post;
import com.project.mini.models.User;
import com.project.mini.repository.PostRepository;
import com.project.mini.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainPageService {

    private final UserRepository userRepository;

    private final PostRepository postRepository;


    public List<RankingResponseDto> rankingList() {
        List<User> allUser = userRepository.findDistinctAllByPostsIsNotNull();

        List<UserRankResponseDto> userRankResponseDtos = new ArrayList<>();

        for (User user : allUser) {
            int happypoint = user.getHappypoint();
            int countPosts = user.getPosts().size();
            Long createdUser = user.getId();
            String nickname = user.getNickname();

            int avePoint = happypoint / countPosts;

            userRankResponseDtos.add(new UserRankResponseDto(avePoint, nickname, countPosts, createdUser));
        }

        userRankResponseDtos.sort(Comparator.comparingInt(UserRankResponseDto::getAvePoint)
                .thenComparingInt(UserRankResponseDto::getCountPosts).reversed()
        );

        List<RankingResponseDto> result = new ArrayList<>();
        for (int i = 0; i < userRankResponseDtos.size(); i++) {
            if (i==5) break;
            result.add(new RankingResponseDto(userRankResponseDtos.get(i).getNickName()));
        }
        return result;
    }

    public List<MainPageResponseDto> showPostList() {
        List<Post> postList = postRepository.findAll();
        List<MainPageResponseDto> list = new ArrayList<>();
        for (Post post : postList) {
            MainPageResponseDto build = MainPageResponseDto.builder()
                    .postId(post.getPostId())
                    .content(post.getContent())
                    .img(post.getImgUrl())
                    .nickname(post.getUser().getNickname())
                    .happypoint(post.getHappypoint())
                    .build();
            list.add(build);
        }
        return list;
    }
}
