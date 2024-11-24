package com.example.anonymous_board.reply.db;

import com.example.anonymous_board.post.db.PostEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "reply")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ReplyEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private PostEntity postEntity;

    private String userName;
    private String password;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime repliedAt;
}
