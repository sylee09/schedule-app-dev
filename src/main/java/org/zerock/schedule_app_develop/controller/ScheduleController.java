package org.zerock.schedule_app_develop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.schedule_app_develop.dto.LoginSessionAttribute;
import org.zerock.schedule_app_develop.dto.ScheduleCreateRequestDto;
import org.zerock.schedule_app_develop.dto.ScheduleResponseDto;
import org.zerock.schedule_app_develop.dto.ScheduleUpdateRequestDto;
import org.zerock.schedule_app_develop.service.ScheduleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleCreateRequestDto dto, @SessionAttribute("login") LoginSessionAttribute login) {
        dto.setUserId(login.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(dto));
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules(@SessionAttribute("login")  LoginSessionAttribute login) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.viewAllSchedules());
    }

    @GetMapping("/schedules/{id}")
    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable Long id, @SessionAttribute("login") LoginSessionAttribute login) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.viewSchedule(id));
    }

    @PatchMapping("/schedules/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id, @RequestBody ScheduleUpdateRequestDto dto, @SessionAttribute("login") LoginSessionAttribute login) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.updateSchedule(id, dto, login));
    }

    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, @SessionAttribute("login") LoginSessionAttribute login) {
        scheduleService.deleteSchedule(id, login);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
