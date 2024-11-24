package com.example.anonymous_board.post.model;

import com.example.anonymous_board.reply.db.ReplyEntity;
import com.example.anonymous_board.reply.model.ReplyDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PostDto {
    private Long id;
    private String userName;
    private String password;
    private String title;
    private String content;
    private LocalDateTime postedAt;
    private List<ReplyDto> replyList = List.of();
}
