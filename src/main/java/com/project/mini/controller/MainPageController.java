package com.project.mini.controller;

import com.project.mini.models.Post;
import com.project.mini.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainPageController {

    private final PostRepository postRepository;

    @GetMapping("/api/postList")
    public List<Post> showPostList() {
        return postRepository.findAll();
    }

    @GetMapping("/api/ranking")
    public void rankingList() {

    }
}
