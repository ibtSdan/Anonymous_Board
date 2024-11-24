package com.example.anonymous_board.reply.service;

import com.example.anonymous_board.post.db.PostRepository;
import com.example.anonymous_board.reply.db.ReplyEntity;
import com.example.anonymous_board.reply.db.ReplyRepository;
import com.example.anonymous_board.reply.model.ReplyDto;
import com.example.anonymous_board.reply.model.ReplyRequest;
import com.example.anonymous_board.reply.model.ReplyUpdate;
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

    public ReplyDto update(ReplyUpdate replyUpdate) {
        // 댓글 및 비밀번호 확인
        var entity = replyRepository.findById(replyUpdate.getReplyId())
                .map( it -> {
                    if(!it.getPassword().equals(replyUpdate.getPassword())){
                        throw new RuntimeException("비밀번호가 다릅니다.");
                    }
                    return it;
                })
                .orElseThrow( () -> {
                    return new RuntimeException("존재하지 않는 댓글 번호 입니다.");
                });
        if(entity.getContent().equals(replyUpdate.getContent())){
            throw new RuntimeException("수정된 내용이 없습니다.");
        }
        entity.setContent(replyUpdate.getContent());
        entity.setRepliedAt(LocalDateTime.now());
        return replyConverter.toDto(replyRepository.save(entity));
    }
}
