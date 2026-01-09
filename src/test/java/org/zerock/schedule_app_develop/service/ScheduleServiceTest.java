package org.zerock.schedule_app_develop.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.schedule_app_develop.dto.ScheduleCreateRequestDto;
import org.zerock.schedule_app_develop.dto.ScheduleResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ScheduleServiceTest {

    @Autowired
    private ScheduleService scheduleService;

    @Test
    @DisplayName("createSchedule")
    void createSchedule() {
        ScheduleCreateRequestDto dto = new ScheduleCreateRequestDto("lee", "제목", "내용");
        // 예외가 발생하지 않는다고 기대
        assertDoesNotThrow(() -> scheduleService.createSchedule(dto));
    }

    @Test
    @DisplayName("viewAllSchedules")
    void viewAllSchedules() {
        ScheduleCreateRequestDto dto = new ScheduleCreateRequestDto("lee", "제목", "내용");
        scheduleService.createSchedule(dto);
        ScheduleCreateRequestDto dto1 = new ScheduleCreateRequestDto("lee", "제목", "내용");
        scheduleService.createSchedule(dto1);
        ScheduleCreateRequestDto dto2 = new ScheduleCreateRequestDto("lee", "제목", "내용");
        scheduleService.createSchedule(dto2);

        List<ScheduleResponse> scheduleResponses = scheduleService.viewAllSchedules();
        assertThat(scheduleResponses.size()).isEqualTo(3);
        assertThat(scheduleResponses.get(0).getUserName()).isEqualTo("lee");
        assertThat(scheduleResponses.get(0).getSubject()).isEqualTo("제목");
        assertThat(scheduleResponses.get(0).getContent()).isEqualTo("내용");
        assertThat(scheduleResponses.get(0).getStartTime()).isNotNull();
        assertThat(scheduleResponses.get(0).getModifiedTime()).isNotNull();
    }
}