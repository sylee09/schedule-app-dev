package org.zerock.schedule_app_develop.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.zerock.schedule_app_develop.dto.UserCreateRequestDto;
import org.zerock.schedule_app_develop.dto.UserUpdateRequestDto;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;

    public User(UserCreateRequestDto dto) {
        this.username = dto.getUsername();
        this.email = dto.getEmail();
    }

    public void change(UserUpdateRequestDto dto) {
        this.username = dto.getUsername();
        this.email = dto.getEmail();
    }
}
