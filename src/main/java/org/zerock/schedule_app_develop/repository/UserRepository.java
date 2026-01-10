package org.zerock.schedule_app_develop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.schedule_app_develop.dto.LoginRequestDto;
import org.zerock.schedule_app_develop.dto.UserResponseDto;
import org.zerock.schedule_app_develop.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select new org.zerock.schedule_app_develop.dto.UserResponseDto(u.id, u.username, u.email, u.createTime,u.updateTime) from User u where u.email=:email and u.password=:password")
    Optional<UserResponseDto> findByEmailAndPassword(@Param("email") String email, @Param("password") String password);
}
