package org.zerock.schedule_app_develop.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.schedule_app_develop.dto.*;
import org.zerock.schedule_app_develop.entity.User;
import org.zerock.schedule_app_develop.error.ScheduleNotFoundException;
import org.zerock.schedule_app_develop.repository.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ScheduleServiceTest {

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = userRepository.save(new User(new UserCreateRequestDto("tester", "tester@gmail.com")));
        ScheduleCreateRequestDto dto = new ScheduleCreateRequestDto("제목", "내용",user.getId());
        scheduleService.createSchedule(dto);
        ScheduleCreateRequestDto dto1 = new ScheduleCreateRequestDto( "제목", "내용",user.getId());
        scheduleService.createSchedule(dto1);
        ScheduleCreateRequestDto dto2 = new ScheduleCreateRequestDto( "제목", "내용",user.getId());
        scheduleService.createSchedule(dto2);
    }

    @Test
    @DisplayName("createSchedule")
    void createSchedule() {
        ScheduleCreateRequestDto dto = new ScheduleCreateRequestDto( "제목", "내용",user.getId());
        // 예외가 발생하지 않는다고 기대
        assertDoesNotThrow(() -> scheduleService.createSchedule(dto));
    }

    @Test
    @DisplayName("viewAllSchedules")
    void viewAllSchedules() {
        List<ScheduleResponseDto> scheduleResponseDtos = scheduleService.viewAllSchedules();
        assertThat(scheduleResponseDtos.size()).isEqualTo(3);
        assertThat(scheduleResponseDtos.get(0).getSubject()).isEqualTo("제목");
        assertThat(scheduleResponseDtos.get(0).getContent()).isEqualTo("내용");
        assertThat(scheduleResponseDtos.get(0).getCreatedTime()).isNotNull();
        assertThat(scheduleResponseDtos.get(0).getModifiedTime()).isNotNull();
        assertThat(scheduleResponseDtos.get(0).getModifiedTime()).isEqualTo(scheduleResponseDtos.get(0).getCreatedTime());
    }

    @Test
    @DisplayName("viewSchedule")
    void viewSchedule() {
        ScheduleResponseDto scheduleResponseDto = scheduleService.viewSchedule(1L);
        assertThat(scheduleResponseDto.getCreatedTime()).isNotNull();

        assertThatThrownBy(() -> scheduleService.viewSchedule(100L)).isInstanceOf(ScheduleNotFoundException.class);
    }

    @Test
    @DisplayName("updateSchedule")
    void updateSchedule() {
        ScheduleCreateRequestDto dto = new ScheduleCreateRequestDto("제목", "내용",user.getId());
        ScheduleResponseDto scheduleResponse = scheduleService.createSchedule(dto);

        ScheduleUpdateRequestDto scheduleUpdateRequestDto = new ScheduleUpdateRequestDto("수정", "수정");
        scheduleService.updateSchedule(scheduleResponse.getId(), scheduleUpdateRequestDto);
        ScheduleResponseDto scheduleResponseDto = scheduleService.viewSchedule(scheduleResponse.getId());
        assertThat(scheduleResponseDto.getSubject()).isEqualTo("수정");
        assertThat(scheduleResponseDto.getContent()).isEqualTo("수정");
        assertThat(scheduleResponseDto.getModifiedTime()).isNotEqualTo(scheduleResponseDto.getCreatedTime());
    }

    @Test
    @DisplayName("deleteSchedule")
    void deleteSchedule() {
        ScheduleCreateRequestDto dto = new ScheduleCreateRequestDto("제목", "내용",user.getId());
        ScheduleResponseDto scheduleResponse = scheduleService.createSchedule(dto);
        scheduleService.deleteSchedule(scheduleResponse.getId());
        assertThatThrownBy(()->scheduleService.viewSchedule(scheduleResponse.getId())).isInstanceOf(ScheduleNotFoundException.class);
    }
}