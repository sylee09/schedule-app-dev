package org.zerock.schedule_app_develop.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.zerock.schedule_app_develop.dto.ScheduleCreateRequestDto;

@Entity
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Schedule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String subject;
    private String content;

    public Schedule(ScheduleCreateRequestDto dto) {
        this.userName = dto.getUserName();
        this.subject = dto.getSubject();
        this.content = dto.getContent();
    }
}
