package org.zerock.schedule_app_develop.dto;

import lombok.Getter;

@Getter
public class ScheduleUpdateRequestDto {
    private String subject;
    private String content;

    public ScheduleUpdateRequestDto(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }
}
