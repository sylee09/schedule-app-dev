package org.zerock.schedule_app_develop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.schedule_app_develop.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
