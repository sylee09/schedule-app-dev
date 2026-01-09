package org.zerock.schedule_app_develop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.schedule_app_develop.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
