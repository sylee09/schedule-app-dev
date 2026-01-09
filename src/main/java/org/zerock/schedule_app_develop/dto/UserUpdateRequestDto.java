package org.zerock.schedule_app_develop.dto;

import lombok.Getter;

@Getter
public class UserUpdateRequestDto {
    private String username;
    private String email;

    public UserUpdateRequestDto(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
