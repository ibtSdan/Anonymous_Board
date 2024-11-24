package com.example.anonymous_board.reply.controller;

import com.example.anonymous_board.reply.model.ReplyDelete;
import com.example.anonymous_board.reply.model.ReplyDto;
import com.example.anonymous_board.reply.model.ReplyRequest;
import com.example.anonymous_board.reply.model.ReplyUpdate;
import com.example.anonymous_board.reply.service.ReplyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reply")
@RequiredArgsConstructor

public class ReplyApiController {
    private final ReplyService replyService;

    // 생성
    @PostMapping("/create")
    public ReplyDto create(@Valid @RequestBody ReplyRequest replyRequest){
        return replyService.create(replyRequest);
    }

    // 댓글 조회는 글 조회 시 조회됨 -> 필요x

    // 수정
    @PostMapping("/update")
    public ReplyDto update(@Valid @RequestBody ReplyUpdate replyUpdate){
        return replyService.update(replyUpdate);
    }

    // 삭제
    @PostMapping("/delete")
    public void delete(@Valid @RequestBody ReplyDelete replyDelete){
        replyService.delete(replyDelete);
    }

}
