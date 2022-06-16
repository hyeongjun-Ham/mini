package com.project.mini.controller;

import com.project.mini.dto.request.PostDto;
import com.project.mini.dto.response.PostDetailResponseDto;
import com.project.mini.dto.response.PostIdResponseDto;
import com.project.mini.security.UserDetailsImpl;
import com.project.mini.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService service;

    //디테일 페이지 게시글 조회
    @GetMapping("/api/postdetail/{postid}")
    public PostDetailResponseDto getPost(@PathVariable Long postid) {
        return service.getDetailPost(postid);
    }

    //게시글 작성
    @PostMapping("/api/post")
    public PostIdResponseDto registerPost(@RequestPart("img") MultipartFile multipartFile,
                                          @RequestParam("happypoint") int happypoint,
                                          @RequestParam("content") String content, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostDto dto = new PostDto(happypoint, content);

        return new PostIdResponseDto(service.register(dto, userDetails, multipartFile));
    }

    //게시글 수정
    @PutMapping("/api/post/{postid}")
    public void ModifyPost(@PathVariable Long postid,
                           @RequestPart(value = "img", required = false) MultipartFile multipartFile,
                           @RequestParam("happypoint") int happypoint,
                           @RequestParam("content") String content, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostDto dto = new PostDto(happypoint, content);
        service.modifyPost(postid, dto, multipartFile, userDetails);
    }

    //게시글 삭제
    @DeleteMapping("/api/post/{postid}")
    public void DeletePost(@PathVariable Long postid, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        service.deletePost(postid, userDetails);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("아이디가 없습니다.");
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException e) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("로그인 해주세요");
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<String> handleMultipartException(MultipartException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 파일 형식입니다.");
    }
}
