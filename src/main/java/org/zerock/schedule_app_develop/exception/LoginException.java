package org.zerock.schedule_app_develop.exception;

public class LoginException extends RuntimeException {
    public LoginException() {
    }

    public LoginException(String message) {
        super(message);
    }
}
