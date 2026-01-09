package org.zerock.schedule_app_develop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.schedule_app_develop.dto.ScheduleCreateRequestDto;
import org.zerock.schedule_app_develop.dto.ScheduleResponse;
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
    public void createSchedule(ScheduleCreateRequestDto dto) {
        scheduleRepository.save(new Schedule(dto));
    }

    public List<ScheduleResponse> viewAllSchedules() {
        return scheduleRepository.findAll()
                .stream()
                .map(schedule -> new ScheduleResponse(schedule))
                .toList();
    }

    public ScheduleResponse viewSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ScheduleNotFoundException("Schedule not found"));
        return new ScheduleResponse(schedule);
    }

    @Transactional
    public ScheduleResponse updateSchedule(Long id, ScheduleUpdateRequestDto dto) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ScheduleNotFoundException("Schedule not found"));
        schedule.setSubject(dto.getSubject());
        schedule.setContent(dto.getContent());
        scheduleRepository.saveAndFlush(schedule);
        return new ScheduleResponse(schedule);
    }

    @Transactional
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

}
