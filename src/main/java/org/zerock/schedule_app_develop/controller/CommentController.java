package org.zerock.schedule_app_develop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.schedule_app_develop.dto.CommentRequestDto;
import org.zerock.schedule_app_develop.dto.CommentResponseDto;
import org.zerock.schedule_app_develop.dto.LoginRequestDto;
import org.zerock.schedule_app_develop.dto.LoginSessionAttribute;
import org.zerock.schedule_app_develop.entity.User;
import org.zerock.schedule_app_develop.service.CommentService;
import org.zerock.schedule_app_develop.service.ScheduleService;
import org.zerock.schedule_app_develop.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;
    private final ScheduleService scheduleService;

    @GetMapping("/schedules/comments")
    public ResponseEntity<List<CommentResponseDto>> getComments(@SessionAttribute("login") LoginSessionAttribute login) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getComments());
    }

    @PostMapping("/schedules/{id}/comments")
    public ResponseEntity<CommentResponseDto> createComment(@SessionAttribute("login") LoginSessionAttribute login, @RequestBody CommentRequestDto commentRequestDto, @PathVariable("id") Long scheduleId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(commentRequestDto, scheduleId, login));
    }
}
