package com.example.anonymous_board.reply.service;

import com.example.anonymous_board.exception.InvalidCredentialsException;
import com.example.anonymous_board.exception.NoChangesException;
import com.example.anonymous_board.post.common.Api;
import com.example.anonymous_board.post.db.PostRepository;
import com.example.anonymous_board.reply.db.ReplyEntity;
import com.example.anonymous_board.reply.db.ReplyRepository;
import com.example.anonymous_board.reply.model.ReplyDelete;
import com.example.anonymous_board.reply.model.ReplyDto;
import com.example.anonymous_board.reply.model.ReplyRequest;
import com.example.anonymous_board.reply.model.ReplyUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    private final ReplyConverter replyConverter;

    public Api<ReplyDto> create(ReplyRequest replyRequest) {
        var post = postRepository.findById(replyRequest.getPostId())
                .orElseThrow( () -> {
                    return new NoSuchElementException("존재하지 않는 글번호 입니다. id = "+replyRequest.getPostId());
                });
        var entity = ReplyEntity.builder()
                .userName(replyRequest.getUserName())
                .password(replyRequest.getPassword())
                .content(replyRequest.getContent())
                .repliedAt(LocalDateTime.now())
                .post(post)
                .build();
        var dto = replyConverter.toDto(replyRepository.save(entity));
        return Api.<ReplyDto>builder()
                .resultCode(String.valueOf(HttpStatus.OK.value()))
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data(dto)
                .build();
    }

    public Api<ReplyDto> update(ReplyUpdate replyUpdate) {
        // 댓글 및 비밀번호 확인
        var entity = replyRepository.findById(replyUpdate.getReplyId())
                .map( it -> {
                    if(!it.getPassword().equals(replyUpdate.getPassword())){
                        throw new InvalidCredentialsException("비밀번호가 다릅니다.");
                    }
                    return it;
                })
                .orElseThrow( () -> {
                    return new NoSuchElementException("존재하지 않는 댓글 번호 입니다. id = "+replyUpdate.getReplyId());
                });
        if(entity.getContent().equals(replyUpdate.getContent())){
            throw new NoChangesException("수정된 내용이 없습니다.");
        }
        entity.setContent(replyUpdate.getContent());
        entity.setRepliedAt(LocalDateTime.now());
        var dto = replyConverter.toDto(replyRepository.save(entity));
        return Api.<ReplyDto>builder()
                .resultCode(String.valueOf(HttpStatus.OK.value()))
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data(dto)
                .build();
    }

    public Api<String> delete(ReplyDelete replyDelete) {
        var entity = replyRepository.findById(replyDelete.getReplyId())
                .map( it -> {
                    if(!it.getPassword().equals(replyDelete.getPassword())){
                        throw new InvalidCredentialsException("비밀번호가 다릅니다.");
                    }
                    replyRepository.delete(it);
                    return "";
                })
                .orElseThrow( () -> {
                    return new NoSuchElementException("존재하지 않는 댓글 번호 입니다. id = "+replyDelete.getReplyId());
                });
        return Api.<String>builder()
                .resultCode(String.valueOf(HttpStatus.OK.value()))
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data(replyDelete.getReplyId()+"번째 댓글이 삭제되었습니다.")
                .build();
    }
}
