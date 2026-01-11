package org.zerock.schedule_app_develop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.schedule_app_develop.dto.LoginSessionAttribute;
import org.zerock.schedule_app_develop.dto.ScheduleCreateRequestDto;
import org.zerock.schedule_app_develop.dto.ScheduleResponseDto;
import org.zerock.schedule_app_develop.dto.ScheduleUpdateRequestDto;
import org.zerock.schedule_app_develop.entity.Schedule;
import org.zerock.schedule_app_develop.entity.User;
import org.zerock.schedule_app_develop.exception.ScheduleNotFoundException;
import org.zerock.schedule_app_develop.exception.UnAuthorizedException;
import org.zerock.schedule_app_develop.exception.UserNotFoundException;
import org.zerock.schedule_app_develop.repository.CommentRepository;
import org.zerock.schedule_app_develop.repository.ScheduleRepository;
import org.zerock.schedule_app_develop.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public ScheduleResponseDto createSchedule(ScheduleCreateRequestDto dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new UserNotFoundException("user not found"));
        Schedule save = scheduleRepository.save(new Schedule(dto, user));
        return new ScheduleResponseDto(save);
    }

    public Page<ScheduleResponseDto> viewAllSchedules(Pageable pageable) {
        return scheduleRepository.findAll(pageable)
                .map(schedule -> new ScheduleResponseDto(schedule));
    }

    public ScheduleResponseDto viewSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ScheduleNotFoundException("Schedule not found"));
        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleUpdateRequestDto dto, LoginSessionAttribute login) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ScheduleNotFoundException("Schedule not found"));
        if (login.getId() != schedule.getUser().getId()) {
            throw new UnAuthorizedException("권한 없음");
        }
        schedule.modify(dto);
        schedule = scheduleRepository.saveAndFlush(schedule);
        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    public void deleteSchedule(Long id, LoginSessionAttribute login) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ScheduleNotFoundException("Schedule not found"));
        if(schedule.getUser().getId() != login.getId()) {
            throw new UnAuthorizedException("권한 없음");
        }
        // 참조 무결성으로 인해 연관된 것들 먼저 제거.
        commentRepository.deleteByScheduleId(id);
        scheduleRepository.deleteById(id);
    }

}
