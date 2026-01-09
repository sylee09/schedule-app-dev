package org.zerock.schedule_app_develop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.schedule_app_develop.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("select s from Schedule s left join fetch s.user where s.id=:id")
    Optional<Schedule> findById(Long id);

    @Query("select s from Schedule s left join fetch s.user")
    List<Schedule> findAll();
}
