package org.zerock.schedule_app_develop.error;

public class ScheduleNotFoundException extends RuntimeException {
    public ScheduleNotFoundException() {
    }

    public ScheduleNotFoundException(String message) {
        super(message);
    }
}
