package org.zerock.schedule_app_develop.dto;

import lombok.Getter;
import org.zerock.schedule_app_develop.entity.Schedule;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponse {
    private String userName;
    private String subject;
    private String content;
    private LocalDateTime startTime;
    private LocalDateTime modifiedTime;

    public ScheduleResponse(Schedule schedule) {
        this.userName = schedule.getUserName();
        this.subject = schedule.getSubject();
        this.content = schedule.getContent();
        this.startTime = schedule.getCreateTime();
        this.modifiedTime = schedule.getUpdateTime();
    }
}
