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
import org.zerock.schedule_app_develop.dto.ScheduleUpdateRequestDto;
import org.zerock.schedule_app_develop.error.ScheduleNotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    @DisplayName("getAllSchedules")
    void getAllSchedules() {
        ScheduleCreateRequestDto dto1 = new ScheduleCreateRequestDto("lee", "제목", "내용");
        scheduleController.createSchedule(dto1);
        ScheduleCreateRequestDto dto2 = new ScheduleCreateRequestDto("lee", "제목", "내용");
        scheduleController.createSchedule(dto2);

        ResponseEntity<List<ScheduleResponseDto>> allSchedules = scheduleController.getAllSchedules();
        assertThat(allSchedules.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(allSchedules.getBody().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("getScheduleById")
    void getScheduleById() {
        ScheduleCreateRequestDto dto1 = new ScheduleCreateRequestDto("lee", "제목", "내용");
        ResponseEntity<ScheduleResponseDto> schedule = scheduleController.createSchedule(dto1);
        ResponseEntity<ScheduleResponseDto> scheduleById = scheduleController.getScheduleById(schedule.getBody().getId());
        assertThat(scheduleById.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(scheduleById.getBody().getCreatedTime()).isEqualTo(schedule.getBody().getModifiedTime());
        assertThat(scheduleById.getBody().getSubject()).isEqualTo(schedule.getBody().getSubject());

        assertThatThrownBy(() -> scheduleController.getScheduleById(10000L)).isInstanceOf(ScheduleNotFoundException.class);
    }

    @Test
    @DisplayName("updateSchedule")
    void updateSchedule() {
        ScheduleCreateRequestDto dto1 = new ScheduleCreateRequestDto("lee", "제목", "내용");
        ResponseEntity<ScheduleResponseDto> schedule = scheduleController.createSchedule(dto1);
        ScheduleUpdateRequestDto scheduleUpdateRequestDto = new ScheduleUpdateRequestDto("modified", "modified");
        ResponseEntity<ScheduleResponseDto> updated = scheduleController.updateSchedule(schedule.getBody().getId(), scheduleUpdateRequestDto);
        assertThat(updated.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updated.getBody().getModifiedTime()).isAfter(updated.getBody().getCreatedTime());
        assertThat(updated.getBody().getSubject()).isEqualTo(scheduleUpdateRequestDto.getSubject());

        assertThatThrownBy(() -> scheduleController.updateSchedule(10000L, scheduleUpdateRequestDto)).isInstanceOf(ScheduleNotFoundException.class);
    }
}