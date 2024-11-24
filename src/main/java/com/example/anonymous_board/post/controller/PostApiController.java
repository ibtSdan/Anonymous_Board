package com.example.anonymous_board.post.controller;

import com.example.anonymous_board.post.model.PostDto;
import com.example.anonymous_board.post.model.PostRequest;
import com.example.anonymous_board.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor

public class PostApiController {
    private final PostService postService;

    // 생성
    @PostMapping("/create")
    public PostDto create(@Valid @RequestBody PostRequest postRequest){
        return postService.create(postRequest);
    }
}
