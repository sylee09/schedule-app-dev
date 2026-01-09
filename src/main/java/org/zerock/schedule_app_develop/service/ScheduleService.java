package org.zerock.schedule_app_develop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.schedule_app_develop.dto.ScheduleCreateRequestDto;
import org.zerock.schedule_app_develop.dto.ScheduleResponse;
import org.zerock.schedule_app_develop.entity.Schedule;
import org.zerock.schedule_app_develop.error.ScheduleNotFoundException;
import org.zerock.schedule_app_develop.repository.ScheduleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    @Autowired
    private final ScheduleRepository scheduleRepository;

    public void createSchedule(ScheduleCreateRequestDto dto) {
        scheduleRepository.save(new Schedule(dto));
    }

    public List<ScheduleResponse> viewAllSchedules() {
        return scheduleRepository.findAll()
                .stream()
                .map(schedule -> new ScheduleResponse(schedule))
                .toList();
    }

}
