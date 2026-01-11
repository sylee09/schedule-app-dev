package org.zerock.schedule_app_develop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.schedule_app_develop.dto.LoginSessionAttribute;
import org.zerock.schedule_app_develop.dto.ScheduleCreateRequestDto;
import org.zerock.schedule_app_develop.dto.ScheduleResponseDto;
import org.zerock.schedule_app_develop.dto.ScheduleUpdateRequestDto;
import org.zerock.schedule_app_develop.entity.Schedule;
import org.zerock.schedule_app_develop.service.ScheduleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    // 로그인 된 사람만 접근 가능
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleCreateRequestDto dto, @SessionAttribute("login") LoginSessionAttribute login) {
        dto.setUserId(login.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(dto));
    }

    @GetMapping("/schedules")
    // 로그인 된 사람만 접근 가능
    public ResponseEntity<Page<ScheduleResponseDto>> getAllSchedules(@SessionAttribute("login") LoginSessionAttribute login, @RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "updateTime"));
        Page<ScheduleResponseDto> scheduleResponseDtos = scheduleService.viewAllSchedules(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(scheduleResponseDtos);
    }

    @GetMapping("/schedules/{id}")
    // 로그인 된 사람만 접근 가능
    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable Long id, @SessionAttribute("login") LoginSessionAttribute login) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.viewSchedule(id));
    }

    @PatchMapping("/schedules/{id}")
    // 로그인 된 사람만 접근 가능
    // 로그인 된 사람이 수정하고자 하는 일정의 작성자와 같은 경우만 수정 가능
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id, @RequestBody ScheduleUpdateRequestDto dto, @SessionAttribute("login") LoginSessionAttribute login) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.updateSchedule(id, dto, login));
    }

    @DeleteMapping("/schedules/{id}")
    // 로그인 된 사람만 접근 가능
    // 로그인 된 사람이 삭제하고자 하는 일정의 작성자와 같은 경우만 수정 가능
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, @SessionAttribute("login") LoginSessionAttribute login) {
        scheduleService.deleteSchedule(id, login);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
