package com.example.anonymous_board.post.db;

import com.example.anonymous_board.reply.db.ReplyEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "post")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PostEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String password;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime postedAt;

    @OneToMany(mappedBy = "post")
    private List<ReplyEntity> replyList = List.of();
}
