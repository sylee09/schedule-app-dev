package org.zerock.schedule_app_develop.exception;

public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException() {
    }
    public UnAuthorizedException(String message) {
        super(message);
    }
}
