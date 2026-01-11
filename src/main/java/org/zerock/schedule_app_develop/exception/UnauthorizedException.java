package org.zerock.schedule_app_develop.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
    }
    public UnauthorizedException(String message) {
        super(message);
    }
}
