package org.zerock.schedule_app_develop.service;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.schedule_app_develop.dto.LoginSessionAttribute;
import org.zerock.schedule_app_develop.dto.UserCreateRequestDto;
import org.zerock.schedule_app_develop.dto.UserResponseDto;
import org.zerock.schedule_app_develop.dto.UserUpdateRequestDto;
import org.zerock.schedule_app_develop.exception.UnauthorizedException;
import org.zerock.schedule_app_develop.exception.UserNotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void createUser() {
        UserCreateRequestDto dto = new UserCreateRequestDto("test", "test@google.com","12345678");
        UserResponseDto user = userService.createUser(dto);
        assertThat(user.getCreateTime()).isEqualTo(user.getUpdateTime());
        assertThat(user.getUsername()).isEqualTo("test");
        assertThat(user.getId()).isNotNull();
    }

    @Test
    void findAll() {
        UserCreateRequestDto dto = new UserCreateRequestDto("test", "test@google.com","12345678");
        UserResponseDto user = userService.createUser(dto);
        UserResponseDto user1 = userService.createUser(dto);

        List<UserResponseDto> users = userService.findAll();
        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    void findById() {
        UserCreateRequestDto dto = new UserCreateRequestDto("test", "test@google.com","12345678");
        UserResponseDto user = userService.createUser(dto);

        UserResponseDto found = userService.findById(user.getId());
        assertThat(found.getUsername()).isEqualTo("test");

        assertThatThrownBy(()->userService.findById(1000L)).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void updateUser() {
        UserCreateRequestDto dto = new UserCreateRequestDto("test", "test@google.com","12345678");
        UserResponseDto user = userService.createUser(dto);
        LoginSessionAttribute loginSessionAttribute = new LoginSessionAttribute(user.getId());

        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto("modified", "modified");
        UserResponseDto modify = userService.modify(user.getId(), userUpdateRequestDto, loginSessionAttribute);

        assertThat(modify.getUsername()).isEqualTo("modified");
        assertThat(modify.getUpdateTime()).isAfter(modify.getCreateTime());

        assertThatThrownBy(()->userService.modify(user.getId(), userUpdateRequestDto, null)).isExactlyInstanceOf(UnauthorizedException.class);
    }

    @Test
    void deleteById() {
        UserCreateRequestDto dto = new UserCreateRequestDto("test", "test@google.com","12345678");
        UserResponseDto user = userService.createUser(dto);
        LoginSessionAttribute loginSessionAttribute = new LoginSessionAttribute(user.getId());

        userService.deleteById(user.getId(), loginSessionAttribute);

        assertThatThrownBy(()->userService.findById(user.getId())).isInstanceOf(UserNotFoundException.class);
    }
}