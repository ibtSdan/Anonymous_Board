package com.example.anonymous_board.post.controller;

import com.example.anonymous_board.post.model.PostDelete;
import com.example.anonymous_board.post.model.PostDto;
import com.example.anonymous_board.post.model.PostRequest;
import com.example.anonymous_board.post.model.PostUpdate;
import com.example.anonymous_board.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 조회
    @GetMapping("/{id}")
    public PostDto view(@PathVariable Long id){
        return postService.view(id);
    }

    // 전체 글 조회
    @GetMapping("/all")
    public List<PostDto> all(){
        return postService.all();
    }

    // 수정
    @PostMapping("/update")
    public PostDto create(@Valid @RequestBody PostUpdate postUpdate){
        return postService.update(postUpdate);
    }

    // 삭제
    @PostMapping("/delete")
    public void delete(@Valid @RequestBody PostDelete postDelete){
        postService.delete(postDelete);
    }
}
