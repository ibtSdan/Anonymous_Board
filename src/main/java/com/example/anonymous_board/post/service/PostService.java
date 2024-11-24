package com.example.anonymous_board.post.service;

import com.example.anonymous_board.post.db.PostEntity;
import com.example.anonymous_board.post.db.PostRepository;
import com.example.anonymous_board.post.model.PostDto;
import com.example.anonymous_board.post.model.PostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class PostService {
    private final PostRepository postRepository;
    private final PostConverter postConverter;


    public PostDto create(PostRequest postRequest) {
        var entity = PostEntity.builder()
                .userName(postRequest.getUserName())
                .password(postRequest.getPassword())
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .postedAt(LocalDateTime.now())
                .build();
        var saveEntity = postRepository.save(entity);
        return postConverter.toDto(saveEntity);
    }

    public PostDto view(Long id) {
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> {
                    return new RuntimeException("해당 글이 존재하지 않습니다.");
                });
        return postConverter.toDto(postEntity);
    }

    public List<PostDto> all() {
        return postRepository.findAll().stream()
                .map(postConverter::toDto).toList();
    }
}
