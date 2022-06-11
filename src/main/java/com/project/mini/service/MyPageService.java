package com.project.mini.service;

import com.project.mini.models.User;
import com.project.mini.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    public int checkMyRank() {
        User user = userRepository.findById(1L).orElseThrow(
                () -> new IllegalArgumentException("아이디가 없습니다.")
        );
        //타겟의 게시글 수 , 해피포인트
        int targetCountPost = user.getPosts().size();
        int targetHappyPoint = user.getHappypoint();

        int targetAvePoint = targetHappyPoint / targetCountPost;

        //전체 유저리스트
        List<User> all = userRepository.findAll();

        int myRank = 1;
        for (int i=0; i<all.size(); i++){
            //다른 유저의 게시글 수 , 해피포인트
            int countPost = all.get(i).getPosts().size();
            int eachHappyPoint =all.get(i).getHappypoint();

            int myAvePoint = eachHappyPoint / countPost;

            if (targetAvePoint < myAvePoint){
                myRank += 1;
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
