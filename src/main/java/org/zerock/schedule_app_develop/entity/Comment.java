package org.zerock.schedule_app_develop.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 댓글은 일정없이 존재할 수 없다.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Schedule schedule;

    // 댓글은 유저없이 존재할 수 없다.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private User user;

    private String content;

    public Comment(Schedule schedule, User user, String content) {
        this.schedule = schedule;
        this.user = user;
        this.content = content;
    }
}
