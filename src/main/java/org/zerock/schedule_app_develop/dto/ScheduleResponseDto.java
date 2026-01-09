package org.zerock.schedule_app_develop.dto;

import lombok.Getter;
import org.zerock.schedule_app_develop.entity.Schedule;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String userName;
    private String subject;
    private String content;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.userName = schedule.getUser().getUsername();
        this.subject = schedule.getSubject();
        this.content = schedule.getContent();
        this.createdTime = schedule.getCreateTime();
        this.modifiedTime = schedule.getUpdateTime();
    }
}
