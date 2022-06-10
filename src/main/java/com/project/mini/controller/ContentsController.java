package com.project.mini.controller;

import com.project.mini.domain.Contents;
import com.project.mini.dto.ContentsRequestDto;
import com.project.mini.dto.ContentsResponseDto;
import com.project.mini.repository.ContentsRepository;
import com.project.mini.security.UserDetailsImpl;
import com.project.mini.service.ContentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ContentsController {

    private final ContentsRepository ContentsRepository;
    private final ContentsService ContentsService;

    // 게시글 조회
    @GetMapping("/api/contents")
    public List<ContentsResponseDto> getContents() {
        return ContentsService.getContents();
    }

    // 게시글 디테일 조회
    @GetMapping("/api/contents/{id}")
    public Contents getContents(@PathVariable Long id) {
        Contents contents =  ContentsRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("id가 존재하지 않습니다."));
        return contents;
    }

    // 게시글 작성
    @PostMapping("/api/contents")
    public Contents createContents(@RequestBody ContentsRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null)
            throw new IllegalStateException("로그인한 유저 정보가 없습니다.");
        // 로그인 되어 있는 ID의 username
        String username = userDetails.getUser().getUsername();
        Contents contents = ContentsService.createContents(requestDto, username);
        return contents;
    }

    @GetMapping("/resource")
    public String resource(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userDetails.getUser().getUsername();
    }
}