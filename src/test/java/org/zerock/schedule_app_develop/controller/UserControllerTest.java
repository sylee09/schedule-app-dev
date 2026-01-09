package org.zerock.schedule_app_develop.controller;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.zerock.schedule_app_develop.dto.UserCreateRequestDto;
import org.zerock.schedule_app_develop.dto.UserResponseDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserController userController;

    @Test
    void createUser() {
        UserCreateRequestDto dto = new UserCreateRequestDto("tester", "test@gmail.com");
        ResponseEntity<UserResponseDto> user = userController.createUser(dto);
        assertThat(user.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(user.getBody().getUsername()).isEqualTo("tester");
    }

}