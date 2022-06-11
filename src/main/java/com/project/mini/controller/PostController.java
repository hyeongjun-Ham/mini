package com.project.mini.controller;

import com.project.mini.dto.request.PostDto;
import com.project.mini.dto.response.PostDetailResponseDto;
import com.project.mini.dto.response.PostResponseDto;
import com.project.mini.models.Post;
import com.project.mini.models.User;
import com.project.mini.repository.PostRepository;
import com.project.mini.repository.UserRepository;
import com.project.mini.security.UserDetailsImpl;
import com.project.mini.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PostController {


    private final PostRepository repo;
    private final UserRepository userRepo;
    private final PostService service;


    //게시글 작성
    @PostMapping("/api/post")
    public PostResponseDto registerPost(@RequestBody PostDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return service.register(dto,userDetails);
    }


    //디테일 페이지 게시글 조회
    @GetMapping("/api/postdetail/{postid}")
    public PostDetailResponseDto getPost(@PathVariable Long postid , @AuthenticationPrincipal UserDetailsImpl userDetails){
        return service.getDetailPost(postid,userDetails);
    }

    @PutMapping("/api/post/{postid}")
    public PostResponseDto ModifyPost(@PathVariable Long postid,@RequestBody PostDto dto,  @AuthenticationPrincipal UserDetailsImpl userDetails){
        return service.modifyPost(postid,dto,userDetails);
    }

    //
    @DeleteMapping("/api/deletepost/{postid}")
    public PostResponseDto DeletePost(@PathVariable Long postid){
        return service.deletePost(postid);
    }
}
