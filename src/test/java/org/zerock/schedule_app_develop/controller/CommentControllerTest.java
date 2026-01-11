package org.zerock.schedule_app_develop.controller;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.zerock.schedule_app_develop.dto.*;
import org.zerock.schedule_app_develop.entity.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentControllerTest {

    @Autowired
    private CommentController commentController;
    @Autowired
    private ScheduleController scheduleController;
    @Autowired
    private UserController userController;

    private Long userId;
    private Long scheduleId;

    @BeforeEach
    void setUp() {
        ResponseEntity<UserResponseDto> userResponse = userController.createUser(new UserCreateRequestDto("tester", "test@gmail.com", "12345678"));
        userId = userResponse.getBody().getId();
        ResponseEntity<ScheduleResponseDto> scheduleResponse = scheduleController.createSchedule(new ScheduleCreateRequestDto("test", "test"), new LoginSessionAttribute(userResponse.getBody().getId()));
        scheduleId = scheduleResponse.getBody().getId();
        commentController.createComment(new LoginSessionAttribute(userId), new CommentRequestDto("댓글"), scheduleId);
        commentController.createComment(new LoginSessionAttribute(userId), new CommentRequestDto("댓글"), scheduleId);
    }

    @Test
    void getComments() {
        ResponseEntity<List<CommentResponseDto>> comments = commentController.getComments(new LoginSessionAttribute(userId));
        assertThat(comments.getBody().size()).isEqualTo(2);
        assertThat(comments.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void createComment() {
        ResponseEntity<CommentResponseDto> comment = commentController.createComment(new LoginSessionAttribute(userId), new CommentRequestDto("댓글"), scheduleId);
        assertThat(comment.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(comment.getBody().getUser().getId()).isEqualTo(userId);
        assertThat(comment.getBody().getSchedule().getId()).isEqualTo(scheduleId);
    }
}