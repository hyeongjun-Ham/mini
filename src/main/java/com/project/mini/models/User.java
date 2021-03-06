package com.project.mini.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String pw;

    @Column
    private int happypoint;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    //happyPoint가 새로운 게시글이 들어왔을때  플러스
    public void setHappypoint(int happypoint) {
        this.happypoint += happypoint;
    }

    public void modifyHappypoint(int tempHappypoint, int happypoint) {
        this.happypoint -= (tempHappypoint - happypoint);
    }

    public User(String username, String nickname, String pw) {
        this.username = username;
        this.nickname = nickname;
        this.pw = pw;
    }

}
