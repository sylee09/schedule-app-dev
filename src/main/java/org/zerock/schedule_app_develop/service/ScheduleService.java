package org.zerock.schedule_app_develop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.schedule_app_develop.dto.ScheduleCreateRequestDto;
import org.zerock.schedule_app_develop.dto.ScheduleResponseDto;
import org.zerock.schedule_app_develop.dto.ScheduleUpdateRequestDto;
import org.zerock.schedule_app_develop.entity.Schedule;
import org.zerock.schedule_app_develop.error.ScheduleNotFoundException;
import org.zerock.schedule_app_develop.repository.ScheduleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {
    @Autowired
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ScheduleResponseDto createSchedule(ScheduleCreateRequestDto dto) {
        Schedule save = scheduleRepository.save(new Schedule(dto));
        return new ScheduleResponseDto(save);
    }

    public List<ScheduleResponseDto> viewAllSchedules() {
        return scheduleRepository.findAll()
                .stream()
                .map(schedule -> new ScheduleResponseDto(schedule))
                .toList();
    }

    public ScheduleResponseDto viewSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ScheduleNotFoundException("Schedule not found"));
        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleUpdateRequestDto dto) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ScheduleNotFoundException("Schedule not found"));
        schedule.modify(dto);
        schedule = scheduleRepository.saveAndFlush(schedule);
        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

}
