package org.zerock.schedule_app_develop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.schedule_app_develop.dto.UserCreateRequestDto;
import org.zerock.schedule_app_develop.dto.UserResponseDto;
import org.zerock.schedule_app_develop.entity.User;
import org.zerock.schedule_app_develop.error.UserNotFoundException;
import org.zerock.schedule_app_develop.repository.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto createUser(UserCreateRequestDto dto){
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



}
