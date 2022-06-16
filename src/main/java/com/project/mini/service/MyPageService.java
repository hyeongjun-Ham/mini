package com.project.mini.service;

import com.project.mini.dto.response.MyPageResponseDto;
import com.project.mini.models.Post;
import com.project.mini.models.User;
import com.project.mini.repository.UserRepository;
import com.project.mini.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;

    public MyPageResponseDto allInfo(UserDetailsImpl userDetails) {
//        User user = userDetails.getUser();
        Long userId = userDetails.getUser().getId();
        User user = userRepository.findById(userId).orElse(null);
        String nickname = user.getNickname();
        List<Post> posts = user.getPosts();

        int myRank = checkMyRank(user);
        int totalUser = checkTotalUser();

        return new MyPageResponseDto(nickname, posts, myRank, totalUser);
    }

    public int checkMyRank(User user) {
        Long targetId = user.getId();

        User target = userRepository.findById(targetId).orElse(null);
        //타겟의 게시글 수 , 해피포인트
        int targetCountPost = target.getPosts().size();
        int targetHappyPoint = target.getHappypoint();

        //게시글 작성 안했을 때 꼴지로 보냄
        if (targetCountPost == 0) {
            return userRepository.findAll().size();
        }

        //해피포인트의 평균
        int targetAvePoint = targetHappyPoint / targetCountPost;

        //게시글이 있는 전체 유저리스트
        List<User> userList = userRepository.findDistinctAllByPostsIsNotNull();

        int myRank = 1;

        for (User otherUser : userList) {
            //다른 유저의 게시글 수 , 해피포인트
            int otherUserCountPost = otherUser.getPosts().size();
            int otherUserEachHappyPoint = otherUser.getHappypoint();

            int OtherUSerAvePoint = otherUserEachHappyPoint / otherUserCountPost;
            // 해피포인트 평균이 적으면 랭킹 +1
            if (targetAvePoint < OtherUSerAvePoint) {
                myRank += 1;
                //해피포인트 평균 같으면 게시글수 비교해서 적으면 랭킹+1
            } else if (targetAvePoint == OtherUSerAvePoint) {
                if (targetCountPost < otherUserCountPost) {
                    myRank += 1;
                    //해피포인트 평균, 게시글 수 전부 같을 때 아이디 생성일 늦으면 랭킹 +1
                } else if (targetCountPost == otherUserCountPost) {
                    if (targetId > otherUser.getId()) {
                        myRank += 1;
                    }
                }
            }
        }
        return myRank;
    }

    public int checkTotalUser() {
        List<User> allUser = userRepository.findAll();
        //전체 유저리스트 갯수
        return allUser.size();
    }
}
