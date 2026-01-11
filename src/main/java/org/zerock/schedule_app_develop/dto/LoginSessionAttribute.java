package org.zerock.schedule_app_develop.dto;

import lombok.Getter;

@Getter
public class LoginSessionAttribute {
    private Long id;

    public LoginSessionAttribute(Long id) {
        this.id = id;
    }
}
