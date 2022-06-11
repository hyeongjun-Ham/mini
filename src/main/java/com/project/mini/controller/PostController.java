package com.project.mini.controller;

import com.project.mini.dto.request.PostDto;
import com.project.mini.dto.response.PostResponseDto;
import com.project.mini.models.Post;
import com.project.mini.repository.PostRepository;
import com.project.mini.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PostController {


    private final PostRepository repo;
    private final PostService service;
    
    @PostMapping("/api/post")
    public PostResponseDto registerPost(@RequestBody PostDto dto){
        return service.register(dto);
    }


    @GetMapping("/api/postdetail/{postid}")
    public void getPost(@PathVariable Long postid){

    }

    @PutMapping("/api/post/{postid}")
    public PostResponseDto ModifyPost(@PathVariable Long postid,@RequestBody PostDto dto){
        //PostResponseDto responseDto = new PostResponseDto(false,"수정 실패");
        PostResponseDto responseDto = new PostResponseDto(true,"수정 완료");

        return responseDto;
    }

    @DeleteMapping("/api/deletepost/{postid}")
    public PostResponseDto DeletePost(@PathVariable Long postid){
        try{
            Optional<Post> post = repo.findByPostId(postid);
            return new PostResponseDto(true,"삭제 완료");
        } catch (Exception e){
            return  new PostResponseDto(false,"삭제 실패");
        }
    }
}
