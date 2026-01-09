package org.zerock.schedule_app_develop.dto;

import lombok.Getter;

@Getter
public class UserCreateRequestDto {
    private String username;
    private String email;

    public UserCreateRequestDto(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
