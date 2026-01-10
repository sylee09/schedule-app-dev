package org.zerock.schedule_app_develop.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class LoginRequestDto {
    private String email;
    @Length(min = 8)
    private String password;

    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
