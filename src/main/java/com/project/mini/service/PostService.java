package com.project.mini.service;

import com.project.mini.dto.request.PostDto;
import com.project.mini.dto.response.PostDetailResponseDto;
import com.project.mini.dto.response.PostResponseDto;
import com.project.mini.models.Post;
import com.project.mini.models.User;
import com.project.mini.repository.PostRepository;
import com.project.mini.repository.UserRepository;
import com.project.mini.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepo;
    private final UserRepository userRepo;
    //게시글 작성
    //로그인한 유저 정보를 받아와서
    //새로등록한 게시글의 happyPoint만큼 +해주고 DB에 업데이트

    public PostResponseDto register(PostDto dto , UserDetailsImpl userDetails){
        String message;

        User user = userRepo.findByUsername(userDetails.getUsername()).get();
            //해피포인트 증가
            //새로운 게시글 등록

            Post post = new Post(dto , user);
            post.getUser().setHappypoint(dto.getHappypoint());
            postRepo.save(post);

            message = "게시글 생성 성공";
            return new PostResponseDto(true,message);
    }

    //디테일 페이지 게시글 조회
    public PostDetailResponseDto getDetailPost(Long postid , UserDetailsImpl userDetails){
            User user = userRepo.findByUsername(userDetails.getUsername()).get();
            Post post = postRepo.findByPostId(postid).get();

        return new PostDetailResponseDto(user,post);
    }

    //게시글 삭제
    public PostResponseDto deletePost(Long postId , UserDetailsImpl userDetails){
        try{
            Post post = postRepo.findByPostId(postId).get();
            //등록되어있던 이미지도 삭제
            s3Service.deleteFile(post.getImgFilename());

            //객체 삭제
            postRepo.delete(post);
            return new PostResponseDto(true,"삭제 성공");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return new PostResponseDto(false,"삭제 실패");
        }
    }

    //게시글 수정
    public PostResponseDto modifyPost(Long postId,PostDto dto,MultipartFile multipartFile,UserDetailsImpl userDetails){
        try{
            //이미지의 수정은 기존에 있던 이미지의 삭제 후 다시등록으로

            //넘겨받은 수정하고 싶은 postId를 받아와서 조회
            Post post = postRepo.findByPostId(postId).get();

            //post를 작성한 유저와 로그인한 유저가 같은 사람이면 수정가능 아니면 예외발생
            validationCheck(post,userDetails);

            //기존 이미지 삭제후 재등록
            s3Service.deleteFile(post.getImgFilename());
            Map<String, String> imgResult = s3Service.uploadFile(multipartFile);

            //엔티티 업데이트
            post.Update(dto, imgResult);

            //해피포인트 수정
            post.getUser().modifyHappypoint(post.getHappypoint(), dto.getHappypoint());

            postRepo.save(post);
            return new PostResponseDto(true,"수정 완료");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return new PostResponseDto(true,"수정 실패");
        }
    }

    //게시글 작성자가 로그인한 유저인지확인
    private void validationCheck(Post post, UserDetailsImpl userDetails){
        if(!post.getUser().getUsername().equals(userDetails.getUsername())) {
            throw new IllegalArgumentException("게시글 작성자만 조작할 수 있습니다.");
        }
    }

    //내가쓴 콘텐츠



}
