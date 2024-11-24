package com.example.anonymous_board.reply.service;

import com.example.anonymous_board.post.db.PostRepository;
import com.example.anonymous_board.reply.db.ReplyEntity;
import com.example.anonymous_board.reply.db.ReplyRepository;
import com.example.anonymous_board.reply.model.ReplyDto;
import com.example.anonymous_board.reply.model.ReplyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    private final ReplyConverter replyConverter;

    public ReplyDto create(ReplyRequest replyRequest) {
        var post = postRepository.findById(replyRequest.getPostId())
                .orElseThrow( () -> {
                    return new RuntimeException("존재하지 않는 글번호 입니다.");
                });
        var entity = ReplyEntity.builder()
                .userName(replyRequest.getUserName())
                .password(replyRequest.getPassword())
                .content(replyRequest.getContent())
                .repliedAt(LocalDateTime.now())
                .post(post)
                .build();
        return replyConverter.toDto(replyRepository.save(entity));
    }
}
