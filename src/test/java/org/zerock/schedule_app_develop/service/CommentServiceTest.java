package org.zerock.schedule_app_develop.service;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.schedule_app_develop.dto.*;
import org.zerock.schedule_app_develop.entity.Comment;
import org.zerock.schedule_app_develop.entity.Schedule;
import org.zerock.schedule_app_develop.entity.User;
import org.zerock.schedule_app_develop.repository.ScheduleRepository;
import org.zerock.schedule_app_develop.repository.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class CommentServiceTest {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Test
    void getComments() {
        User save = userRepository.save(new User("tester", "tester@gmail.com", "12345678"));
        Schedule schedule = new Schedule(new ScheduleCreateRequestDto("test", "test"), save);
        scheduleRepository.save(schedule);

        commentService.createComment(new CommentRequestDto("댓글1"), schedule.getId(), new LoginSessionAttribute(save.getId()));
        commentService.createComment(new CommentRequestDto("댓글2"), schedule.getId(), new LoginSessionAttribute(save.getId()));

        List<CommentResponseDto> comments = commentService.getComments();

        assertThat(comments.size()).isEqualTo(2);
        assertThat(comments.get(0).getUser().getId()).isEqualTo(save.getId());
        assertThat(comments.get(0).getSchedule().getId()).isEqualTo(schedule.getId());
    }

    @Test
    void createComment() {
        User save = userRepository.save(new User("tester", "tester@gmail.com", "12345678"));
        Schedule schedule = new Schedule(new ScheduleCreateRequestDto("test", "test"), save);
        scheduleRepository.save(schedule);

        CommentResponseDto comment = commentService.createComment(new CommentRequestDto("댓글1"), schedule.getId(), new LoginSessionAttribute(save.getId()));

        assertThat(comment.getUser().getId()).isEqualTo(save.getId());
        assertThat(comment.getSchedule().getId()).isEqualTo(schedule.getId());
    }
}