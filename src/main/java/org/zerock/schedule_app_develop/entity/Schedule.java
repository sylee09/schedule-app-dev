package org.zerock.schedule_app_develop.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.zerock.schedule_app_develop.dto.ScheduleCreateRequestDto;
import org.zerock.schedule_app_develop.dto.ScheduleUpdateRequestDto;

@Entity
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Schedule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 일정은 유저 없이 존재할 수 없다.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private User user;

    private String subject;
    private String content;

    public Schedule(ScheduleCreateRequestDto dto, User user) {
        this.subject = dto.getSubject();
        this.content = dto.getContent();
        this.user = user;
    }

    public void modify(ScheduleUpdateRequestDto dto) {
        this.subject = dto.getSubject();
        this.content = dto.getContent();
    }
}
