package org.zerock.schedule_app_develop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.schedule_app_develop.dto.*;
import org.zerock.schedule_app_develop.entity.User;
import org.zerock.schedule_app_develop.exception.LoginException;
import org.zerock.schedule_app_develop.exception.UnauthorizedException;
import org.zerock.schedule_app_develop.exception.UserNotFoundException;
import org.zerock.schedule_app_develop.repository.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto createUser(UserCreateRequestDto dto) {
        User user = new User(dto);
        User save = userRepository.save(user);
        return new UserResponseDto(save);
    }

    public List<UserResponseDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(user->new UserResponseDto(user))
                .toList();
    }

    public UserResponseDto findById(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        return new UserResponseDto(user);
    }

    @Transactional
    public UserResponseDto modify(Long id, UserUpdateRequestDto dto, LoginSessionAttribute login) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (login == null || !user.getId().equals(login.getId())) {
            throw new UnauthorizedException("Unauthorized");
        }

        user.change(dto);

        userRepository.saveAndFlush(user);
        return new UserResponseDto(user);
    }

    @Transactional
    public void deleteById(Long id, LoginSessionAttribute login) {
        if (login == null || !login.getId().equals(id)) {
            throw new UnauthorizedException("Unauthorized");
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public LoginSessionAttribute login(LoginRequestDto loginRequestDto) {
        return userRepository.findByEmailAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword()).orElseThrow(() -> new LoginException("이메일 또는 비밀번호가 틀렸습니다."));
    }
}
