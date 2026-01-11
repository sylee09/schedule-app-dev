package org.zerock.schedule_app_develop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.schedule_app_develop.config.PasswordEncoder;
import org.zerock.schedule_app_develop.dto.*;
import org.zerock.schedule_app_develop.entity.User;
import org.zerock.schedule_app_develop.exception.LoginException;
import org.zerock.schedule_app_develop.exception.UnAuthorizedException;
import org.zerock.schedule_app_develop.exception.UserNotFoundException;
import org.zerock.schedule_app_develop.repository.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto createUser(UserCreateRequestDto dto) {
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        User user = new User(dto.getUsername(), dto.getEmail(), encodedPassword);
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
        if (!user.getId().equals(login.getId())) {
            throw new UnAuthorizedException("Unauthorized");
        }

        user.change(dto);

        userRepository.saveAndFlush(user);
        return new UserResponseDto(user);
    }

    @Transactional
    public void deleteById(Long id, LoginSessionAttribute login) {
        if (!login.getId().equals(id)) {
            throw new UnAuthorizedException("Unauthorized");
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public LoginSessionAttribute login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(() -> new LoginException("Invalid email"));
        if (passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            return new LoginSessionAttribute(user.getId());
        } else {
            throw new LoginException("Invalid password");
        }
    }
}
