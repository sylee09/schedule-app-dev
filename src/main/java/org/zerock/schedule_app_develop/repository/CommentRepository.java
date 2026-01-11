package org.zerock.schedule_app_develop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.schedule_app_develop.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c left join fetch c.schedule left join fetch c.user")
    List<Comment> findAll();

    @Modifying
    @Query("delete from Comment c where c.user.id=:id")
    void deleteByUserId(@Param("id") Long id);

}
