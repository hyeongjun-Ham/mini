package com.project.mini.service;

import com.project.mini.dto.request.PostDto;
import com.project.mini.dto.response.PostResponseDto;
import com.project.mini.models.Post;
import com.project.mini.models.User;
import com.project.mini.repository.PostRepository;
import com.project.mini.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepo;
    private final UserRepository userRepo;
    //게시글 작성
    //로그인한 유저 정보를 받아와서
    //새로등록한 게시글의 happyPoint만큼 +해주고 DB에 업데이트
    public PostResponseDto register(PostDto dto){
        String message;

        try{
            Post post = new Post(dto);
            postRepo.save(post);

            message = "게시글 생성 성공";
            return new PostResponseDto(true,message);
        }
        catch (Exception e){
            message = "게시글 생성 실패";
            return new PostResponseDto(false,message);
        }
    }

    //게시글 수정



    //게시글 삭제



    //내가쓴 콘텐츠



}
