package com.project.mini.service;

import com.project.mini.dto.request.PostDto;
import com.project.mini.dto.response.CommentResponseDto;
import com.project.mini.dto.response.PostDetailResponseDto;
import com.project.mini.models.Comment;
import com.project.mini.models.Post;
import com.project.mini.models.User;
import com.project.mini.repository.CommentRepository;
import com.project.mini.repository.PostRepository;
import com.project.mini.repository.UserRepository;
import com.project.mini.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepo;
    private final UserRepository userRepo;


    private final CommentRepository commentRepository;

    private final AwsS3Service s3Service;

    //게시글 작성
    //로그인한 유저 정보를 받아와서
    //새로등록한 게시글의 happyPoint만큼 +해주고 DB에 업데이트

    public Long register(PostDto dto, UserDetailsImpl userDetails, MultipartFile multipartFile) {

        Map<String, String> imgResult = s3Service.uploadFile(multipartFile);

        User user = userRepo.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("유저가 없습니다.")
        );

        //새로운 게시글 등록
        Post post = new Post(dto, user, imgResult);
        //해피포인트 증가
        post.getUser().setHappypoint(dto.getHappypoint());
        postRepo.save(post);
        return post.getPostId();
    }

    //디테일 페이지 게시글 조회
    public PostDetailResponseDto getDetailPost(Long postid) {
        Post post = postRepo.findByPostId(postid).orElseThrow(
                () -> new IllegalArgumentException("게시글이 없습니다.")
        );
        List<CommentResponseDto> commentList = new ArrayList<>();
        List<Comment> allByPost_postId = commentRepository.findAllByPost_PostId(postid);
        for (Comment comment : allByPost_postId) {
            CommentResponseDto commentResponseDto1 = new CommentResponseDto(comment);
            commentList.add(commentResponseDto1);
        }
        return new PostDetailResponseDto(post, commentList);
    }

    //게시글 삭제

    public void deletePost(Long postId, UserDetailsImpl userDetails) {
        Post post = postRepo.findByPostId(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 없습니다.")
        );

        //post를 작성한 유저와 로그인한 유저가 같은 사람이면 수정가능 아니면 예외발생
        validationCheck(post, userDetails);

        //등록되어있던 이미지도 삭제
        s3Service.deleteFile(post.getTransImgFileName());
        post.getUser().modifyHappypoint(post.getHappypoint(), 0);
        //객체 삭제
        postRepo.delete(post);
    }

    //게시글 수정

    public void modifyPost(Long postId, PostDto dto, MultipartFile multipartFile, UserDetailsImpl userDetails) {
        //이미지의 수정은 기존에 있던 이미지의 삭제 후 다시등록으로

        //넘겨받은 수정하고 싶은 postId를 받아와서 조회
        Post post = postRepo.findByPostId(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 없습니다.")
        );
        //post를 작성한 유저와 로그인한 유저가 같은 사람이면 수정가능 아니면 예외발생
        validationCheck(post, userDetails);
        //해피포인트 수정//해피포인트 수정
        post.getUser().modifyHappypoint(post.getHappypoint(), dto.getHappypoint());
        if (multipartFile != null) {
            //기존 이미지 삭제후 재등록
            s3Service.deleteFile(post.getTransImgFileName());
            Map<String, String> imgResult = s3Service.uploadFile(multipartFile);

            //엔티티 업데이트
            post.Update(dto, imgResult);
        } else {
            //엔티티 업데이트
            post.Update(dto);
        }
        postRepo.save(post);

//        //기존 이미지 삭제후 재등록
//        s3Service.deleteFile(post.getTransImgFileName());
//        Map<String, String> imgResult = s3Service.uploadFile(multipartFile);

//        //해피포인트 수정
//        post.getUser().modifyHappypoint(post.getHappypoint(), dto.getHappypoint());
//
//        //엔티티 업데이트
//        post.Update(dto, imgResult);
//
//        postRepo.save(post);
    }

//    public void modifyPost(Long postId, PostDto dto, UserDetailsImpl userDetails) {
//        //이미지의 수정은 기존에 있던 이미지의 삭제 후 다시등록으로
//
//        //넘겨받은 수정하고 싶은 postId를 받아와서 조회
//        Post post = postRepo.findByPostId(postId).get();
//
//        //post를 작성한 유저와 로그인한 유저가 같은 사람이면 수정가능 아니면 예외발생
//        validationCheck(post, userDetails);
//
//        //해피포인트 수정
//        post.getUser().modifyHappypoint(post.getHappypoint(), dto.getHappypoint());
//
//        //엔티티 업데이트
//        post.Update(dto);
//
//        postRepo.save(post);
//    }

    //게시글 작성자가 로그인한 유저인지확인
    private void validationCheck(Post post, UserDetailsImpl userDetails) {
        if (!post.getUser().getUsername().equals(userDetails.getUsername())) {
            throw new IllegalArgumentException("게시글 작성자만 조작할 수 있습니다.");
        }
    }
}
