package com.example.anonymous_board.post.controller;

import com.example.anonymous_board.post.common.Api;
import com.example.anonymous_board.post.common.ApiPagination;
import com.example.anonymous_board.post.model.PostDelete;
import com.example.anonymous_board.post.model.PostDto;
import com.example.anonymous_board.post.model.PostRequest;
import com.example.anonymous_board.post.model.PostUpdate;
import com.example.anonymous_board.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor

public class PostApiController {
    private final PostService postService;

    // 생성
    @PostMapping("/create")
    public Api<PostDto> create(@Valid @RequestBody PostRequest postRequest){
        return postService.create(postRequest);
    }

    // 조회
    @GetMapping("/{id}")
    public Api<PostDto> view(@PathVariable Long id){
        return postService.view(id);
    }

    // 전체 글 조회
    @GetMapping("/all")
    public Api<ApiPagination<List<PostDto>>> all(
            @PageableDefault(page = 0,size = 10,sort = "id",direction = Sort.Direction.DESC)Pageable pageable
            ){
        return postService.all(pageable);
    }

    // 수정
    @PostMapping("/update")
    public Api<PostDto> create(@Valid @RequestBody PostUpdate postUpdate){
        return postService.update(postUpdate);
    }

    // 삭제
    @PostMapping("/delete")
    public Api<String> delete(@Valid @RequestBody PostDelete postDelete){
        return postService.delete(postDelete);
    }
}
