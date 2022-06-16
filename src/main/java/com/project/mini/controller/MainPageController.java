package com.project.mini.controller;

import com.project.mini.dto.response.MainPageResponseDto;
import com.project.mini.dto.response.RankingResponseDto;
import com.project.mini.security.UserDetailsImpl;
import com.project.mini.service.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainPageController {

    private final MainPageService mainPageService;

    @GetMapping("/api/postList")
    public List<MainPageResponseDto> showPostList() {
        return mainPageService.showPostList();
    }

    @GetMapping("/api/ranking")
    public List<RankingResponseDto> rankingList() {
        return mainPageService.rankingList();
    }
}
