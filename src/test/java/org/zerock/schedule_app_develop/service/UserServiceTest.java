package org.zerock.schedule_app_develop.service;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.schedule_app_develop.dto.UserCreateRequestDto;
import org.zerock.schedule_app_develop.dto.UserResponseDto;
import org.zerock.schedule_app_develop.error.UserNotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void createUser() {
        UserCreateRequestDto dto = new UserCreateRequestDto("test", "test@google.com");
        UserResponseDto user = userService.createUser(dto);
        assertThat(user.getCreateTime()).isEqualTo(user.getUpdateTime());
        assertThat(user.getUsername()).isEqualTo("test");
        assertThat(user.getId()).isNotNull();
    }

    @Test
    void findAll() {
        UserCreateRequestDto dto = new UserCreateRequestDto("test", "test@google.com");
        UserResponseDto user = userService.createUser(dto);
        UserResponseDto user1 = userService.createUser(dto);

        List<UserResponseDto> users = userService.findAll();
        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    void findById() {
        UserCreateRequestDto dto = new UserCreateRequestDto("test", "test@google.com");
        UserResponseDto user = userService.createUser(dto);

        UserResponseDto found = userService.findById(user.getId());
        assertThat(found.getUsername()).isEqualTo("test");

        assertThatThrownBy(()->userService.findById(1000L)).isInstanceOf(UserNotFoundException.class);
    }
}