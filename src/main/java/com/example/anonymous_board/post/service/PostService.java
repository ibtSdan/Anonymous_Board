package com.example.anonymous_board.post.service;

import com.example.anonymous_board.post.db.PostEntity;
import com.example.anonymous_board.post.db.PostRepository;
import com.example.anonymous_board.post.model.PostDto;
import com.example.anonymous_board.post.model.PostRequest;
import com.example.anonymous_board.post.model.PostUpdate;
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


    public PostDto update(PostUpdate postUpdate) {
        // 비밀번호 확인 및 게시글 존재 확인
        var entity =postRepository.findById(postUpdate.getId())
                .map( it -> {
                    // 존재한다면?
                    if(!it.getPassword().equals(postUpdate.getPassword())){
                        throw new RuntimeException("비밀번호가 다릅니다.");
                    }
                    return it;
                }).orElseThrow( () -> {
                    return new RuntimeException("해당 게시글이 존재하지 않습니다.");
                });
        if(entity.getTitle().equals(postUpdate.getTitle()) && entity.getContent().equals(postUpdate.getContent())){
            throw new RuntimeException("수정된 내용이 존재하지 않습니다.");
        }

        // 업데이트 후 저장
        entity.setTitle(postUpdate.getTitle());
        entity.setContent(postUpdate.getContent());
        entity.setPostedAt(LocalDateTime.now());
        var newPost = postRepository.save(entity);
        return postConverter.toDto(newPost);
    }
}
