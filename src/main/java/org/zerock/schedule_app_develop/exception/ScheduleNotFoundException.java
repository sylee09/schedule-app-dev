package org.zerock.schedule_app_develop.exception;

public class ScheduleNotFoundException extends RuntimeException {
    public ScheduleNotFoundException() {
    }

    public ScheduleNotFoundException(String message) {
        super(message);
    }
}
