package org.zerock.schedule_app_develop.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.zerock.schedule_app_develop.dto.ScheduleCreateRequestDto;
import org.zerock.schedule_app_develop.dto.ScheduleResponseDto;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ScheduleControllerTest {

    @Autowired
    private ScheduleController scheduleController;

    @Test
    @DisplayName("createSchedule")
    void createSchedule() {
        ScheduleCreateRequestDto dto = new ScheduleCreateRequestDto("lee", "제목", "내용");
        ResponseEntity<ScheduleResponseDto> schedule = scheduleController.createSchedule(dto);
        assertThat(schedule.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(schedule.getBody().getId()).isNotNull();
        assertThat(schedule.getBody().getContent()).isEqualTo(dto.getContent());
        assertThat(schedule.getBody().getSubject()).isEqualTo(dto.getSubject());
        assertThat(schedule.getBody().getModifiedTime()).isEqualTo(schedule.getBody().getCreatedTime());
    }
}