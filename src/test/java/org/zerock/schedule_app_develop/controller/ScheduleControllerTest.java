package org.zerock.schedule_app_develop.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.zerock.schedule_app_develop.dto.*;
import org.zerock.schedule_app_develop.entity.User;
import org.zerock.schedule_app_develop.exception.ScheduleNotFoundException;
import org.zerock.schedule_app_develop.repository.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class ScheduleControllerTest {

    @Autowired
    private ScheduleController scheduleController;
    private User user;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        user = userRepository.save(new User(new UserCreateRequestDto("tester", "tester@gmail.com", "12345678")));
    }

    @Test
    @DisplayName("createSchedule")
    void createSchedule() {
        ScheduleCreateRequestDto dto = new ScheduleCreateRequestDto("제목", "내용");
        dto.setUserId(user.getId());
        LoginSessionAttribute loginSessionAttribute = new LoginSessionAttribute(dto.getUserId());
        ResponseEntity<ScheduleResponseDto> schedule = scheduleController.createSchedule(dto, loginSessionAttribute);
        assertThat(schedule.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(schedule.getBody().getId()).isNotNull();
        assertThat(schedule.getBody().getContent()).isEqualTo(dto.getContent());
        assertThat(schedule.getBody().getSubject()).isEqualTo(dto.getSubject());
        assertThat(schedule.getBody().getModifiedTime()).isEqualTo(schedule.getBody().getCreatedTime());
    }

    @Test
    @DisplayName("getAllSchedules")
    void getAllSchedules() {
        ScheduleCreateRequestDto dto1 = new ScheduleCreateRequestDto("제목", "내용");
        dto1.setUserId(user.getId());
        LoginSessionAttribute loginSessionAttribute = new LoginSessionAttribute(dto1.getUserId());
        scheduleController.createSchedule(dto1, loginSessionAttribute);

        ScheduleCreateRequestDto dto2 = new ScheduleCreateRequestDto("제목", "내용");
        dto2.setUserId(user.getId());
        LoginSessionAttribute loginSessionAttribute2 = new LoginSessionAttribute(dto2.getUserId());
        scheduleController.createSchedule(dto2, loginSessionAttribute2);

        ResponseEntity<Page<ScheduleResponseDto>> allSchedules = scheduleController.getAllSchedules(loginSessionAttribute, 0, 10);
        assertThat(allSchedules.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(allSchedules.getBody().getTotalElements()).isEqualTo(2);
    }

    @Test
    @DisplayName("getScheduleById")
    void getScheduleById() {
        ScheduleCreateRequestDto dto1 = new ScheduleCreateRequestDto("제목", "내용");
        dto1.setUserId(user.getId());
        LoginSessionAttribute loginSessionAttribute = new LoginSessionAttribute(dto1.getUserId());
        ResponseEntity<ScheduleResponseDto> schedule = scheduleController.createSchedule(dto1, loginSessionAttribute);
        ResponseEntity<ScheduleResponseDto> scheduleById = scheduleController.getScheduleById(schedule.getBody().getId(), loginSessionAttribute);
        assertThat(scheduleById.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(scheduleById.getBody().getCreatedTime()).isEqualTo(schedule.getBody().getModifiedTime());
        assertThat(scheduleById.getBody().getSubject()).isEqualTo(schedule.getBody().getSubject());

        assertThatThrownBy(() -> scheduleController.getScheduleById(10000L, loginSessionAttribute)).isInstanceOf(ScheduleNotFoundException.class);
    }

    @Test
    @DisplayName("updateSchedule")
    void updateSchedule() {
        ScheduleCreateRequestDto dto1 = new ScheduleCreateRequestDto("제목", "내용");
        dto1.setUserId(user.getId());
        LoginSessionAttribute loginSessionAttribute = new LoginSessionAttribute(user.getId());
        ResponseEntity<ScheduleResponseDto> schedule = scheduleController.createSchedule(dto1, loginSessionAttribute);
        ScheduleUpdateRequestDto scheduleUpdateRequestDto = new ScheduleUpdateRequestDto("modified", "modified");
        ResponseEntity<ScheduleResponseDto> updated = scheduleController.updateSchedule(schedule.getBody().getId(), scheduleUpdateRequestDto, loginSessionAttribute);
        assertThat(updated.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updated.getBody().getModifiedTime()).isAfter(updated.getBody().getCreatedTime());
        assertThat(updated.getBody().getSubject()).isEqualTo(scheduleUpdateRequestDto.getSubject());

        assertThatThrownBy(() -> scheduleController.updateSchedule(10000L, scheduleUpdateRequestDto, loginSessionAttribute)).isInstanceOf(ScheduleNotFoundException.class);
    }

    @Test
    @DisplayName("deleteSchedule")
    public void deleteSchedule() {
        ScheduleCreateRequestDto dto1 = new ScheduleCreateRequestDto("제목", "내용");
        dto1.setUserId(user.getId());
        LoginSessionAttribute loginSessionAttribute = new LoginSessionAttribute(dto1.getUserId());
        ResponseEntity<ScheduleResponseDto> schedule = scheduleController.createSchedule(dto1, loginSessionAttribute);
        ResponseEntity<Void> voidResponseEntity = scheduleController.deleteSchedule(schedule.getBody().getId(), loginSessionAttribute);
        assertThat(voidResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        assertThatThrownBy(() -> scheduleController.getScheduleById(schedule.getBody().getId(), loginSessionAttribute)).isInstanceOf(ScheduleNotFoundException.class);
    }
}