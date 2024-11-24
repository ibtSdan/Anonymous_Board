package com.example.anonymous_board.reply.service;

import com.example.anonymous_board.reply.db.ReplyEntity;
import com.example.anonymous_board.reply.model.ReplyDto;
import org.springframework.stereotype.Service;

@Service

public class ReplyConverter {
    public ReplyDto toDto(ReplyEntity replyEntity){
        return ReplyDto.builder()
                .id(replyEntity.getId())
                .userName(replyEntity.getUserName())
                .password(replyEntity.getPassword())
                .content(replyEntity.getContent())
                .repliedAt(replyEntity.getRepliedAt())
                .postId(replyEntity.getPost().getId())
                .build();
    }
}
