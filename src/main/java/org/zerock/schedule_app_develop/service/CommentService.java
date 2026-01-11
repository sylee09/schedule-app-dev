package org.zerock.schedule_app_develop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.zerock.schedule_app_develop.dto.CommentRequestDto;
import org.zerock.schedule_app_develop.dto.CommentResponseDto;
import org.zerock.schedule_app_develop.dto.LoginSessionAttribute;
import org.zerock.schedule_app_develop.entity.Comment;
import org.zerock.schedule_app_develop.entity.Schedule;
import org.zerock.schedule_app_develop.entity.User;
import org.zerock.schedule_app_develop.exception.ScheduleNotFoundException;
import org.zerock.schedule_app_develop.exception.UserNotFoundException;
import org.zerock.schedule_app_develop.repository.CommentRepository;
import org.zerock.schedule_app_develop.repository.ScheduleRepository;
import org.zerock.schedule_app_develop.repository.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public List<CommentResponseDto> getComments() {
        return commentRepository.findAll()
                .stream()
                .map(c -> new CommentResponseDto(c.getId(), c.getSchedule(), c.getUser(), c.getContent(), c.getCreateTime(), c.getUpdateTime()))
                .toList();
    }

    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, Long scheduleId, LoginSessionAttribute login) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new ScheduleNotFoundException("schedule not found"));
        User user = userRepository.findById(login.getId()).orElseThrow(() -> new UserNotFoundException("user not found"));
        Comment comment = new Comment(schedule, user, commentRequestDto.getContent());
        Comment save = commentRepository.save(comment);
        return new CommentResponseDto(save.getId(), save.getSchedule(), save.getUser(), save.getContent(), save.getCreateTime(), save.getUpdateTime());
    }
}
