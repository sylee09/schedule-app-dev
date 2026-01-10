package org.zerock.schedule_app_develop.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.zerock.schedule_app_develop.dto.UserCreateRequestDto;
import org.zerock.schedule_app_develop.dto.UserResponseDto;
import org.zerock.schedule_app_develop.dto.UserUpdateRequestDto;
import org.zerock.schedule_app_develop.exception.UserNotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserController userController;

    @Test
    void createUser() {
        UserCreateRequestDto dto = new UserCreateRequestDto("tester", "test@gmail.com", "12345678");
        ResponseEntity<UserResponseDto> user = userController.createUser(dto);
        assertThat(user.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(user.getBody().getUsername()).isEqualTo("tester");
    }

    @Test
    void getAllUsers() {
        UserCreateRequestDto dto = new UserCreateRequestDto("tester", "test@gmail.com","12345678");
        userController.createUser(dto);
        userController.createUser(dto);

        ResponseEntity<List<UserResponseDto>> allUsers = userController.getAllUsers();
        assertThat(allUsers.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(allUsers.getBody().size()).isEqualTo(2);
    }

    @Test
    void getUserById() {
        UserCreateRequestDto dto = new UserCreateRequestDto("tester", "test@gmail.com","12345678");
        ResponseEntity<UserResponseDto> user = userController.createUser(dto);

        ResponseEntity<UserResponseDto> found = userController.getUser(user.getBody().getId());
        assertThat(user.getBody().getUsername()).isEqualTo("tester");

        assertThatThrownBy(()->userController.getUser(1000L)).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void updateUser() {
        UserCreateRequestDto dto = new UserCreateRequestDto("tester", "test@gmail.com","12345678");
        ResponseEntity<UserResponseDto> user = userController.createUser(dto);
        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto("modify", "modify@google.com");
        ResponseEntity<UserResponseDto> userResponseDtoResponseEntity = userController.updateUser(user.getBody().getId(), userUpdateRequestDto);
        assertThat(userResponseDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(userResponseDtoResponseEntity.getBody().getUsername()).isEqualTo("modify");
        assertThat(userResponseDtoResponseEntity.getBody().getUpdateTime()).isAfter(userResponseDtoResponseEntity.getBody().getCreateTime());

        assertThatThrownBy(() -> userController.updateUser(1000L, userUpdateRequestDto)).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void deleteUser() {
        UserCreateRequestDto dto = new UserCreateRequestDto("tester", "test@gmail.com","12345678");
        ResponseEntity<UserResponseDto> user = userController.createUser(dto);
        userController.deleteUser(user.getBody().getId());

        assertThatThrownBy(() -> userController.getUser(user.getBody().getId())).isInstanceOf(UserNotFoundException.class);
    }

}