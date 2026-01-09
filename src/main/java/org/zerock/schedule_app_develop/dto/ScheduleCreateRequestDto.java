package org.zerock.schedule_app_develop.dto;

import lombok.Getter;

@Getter
public class ScheduleCreateRequestDto {
    private String userName;
    private String subject;
    private String content;

    public ScheduleCreateRequestDto(String userName, String subject, String content) {
        this.userName = userName;
        this.subject = subject;
        this.content = content;
    }
}
