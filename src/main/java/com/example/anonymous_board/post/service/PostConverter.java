package com.example.anonymous_board.post.service;

import com.example.anonymous_board.post.db.PostEntity;
import com.example.anonymous_board.post.model.PostDto;
import com.example.anonymous_board.reply.service.ReplyConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class PostConverter {
    private final ReplyConverter replyConverter;

    public PostDto toDto(PostEntity postEntity){
        var replyList = postEntity.getReplyList() != null
                ? postEntity.getReplyList().stream()
                        .map(replyConverter::toDto).toList()
                : null;


        return PostDto.builder()
                .id(postEntity.getId())
                .userName(postEntity.getUserName())
                .password(postEntity.getPassword())
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .postedAt(postEntity.getPostedAt())
                .replyList(replyList)
                .build();
    }

}
