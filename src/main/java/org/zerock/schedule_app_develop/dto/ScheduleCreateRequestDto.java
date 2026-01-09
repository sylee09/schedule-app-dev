package org.zerock.schedule_app_develop.dto;

import lombok.Getter;
import org.zerock.schedule_app_develop.entity.User;

@Getter
public class ScheduleCreateRequestDto {
    private String subject;
    private String content;
    private Long userId;

    public ScheduleCreateRequestDto(String subject, String content, Long userId) {
        this.subject = subject;
        this.content = content;
        this.userId = userId;
    }
}
