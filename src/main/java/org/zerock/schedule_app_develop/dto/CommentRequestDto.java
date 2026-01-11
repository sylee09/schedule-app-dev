package org.zerock.schedule_app_develop.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String content;

    public CommentRequestDto(String content) {
        this.content = content;
    }
}
