package org.zerock.schedule_app_develop.dto;

import lombok.Getter;
import org.zerock.schedule_app_develop.entity.Schedule;
import org.zerock.schedule_app_develop.entity.User;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;

    private ScheduleResponseDto schedule;

    private UserResponseDto user;

    private String content;

    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;

    public CommentResponseDto(Long id, Schedule schedule, User user, String content, LocalDateTime createdTime, LocalDateTime modifiedTime) {
        this.id = id;
        this.schedule = new ScheduleResponseDto(schedule);
        this.user = new UserResponseDto(user);
        this.content = content;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }
}
