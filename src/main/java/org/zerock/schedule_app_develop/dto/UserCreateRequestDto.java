package org.zerock.schedule_app_develop.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class UserCreateRequestDto {
    private String username;
    @Email(message = "이메일 형식이어야 합니다.")
    private String email;
    @Length(min = 8,message = "길이가 8 이상이어야 합니다.")
    private String password;

    public UserCreateRequestDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
