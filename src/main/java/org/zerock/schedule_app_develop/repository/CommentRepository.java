package org.zerock.schedule_app_develop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.schedule_app_develop.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // commentResponseDto가 comment와 연관된 schedule의 정보와 user의 정보가 필요하므로 fetch join을 사용하여 한번에 정보들을 영속성 컨텍스트에 가져와 불필요한 추가 쿼리 없앰
    @Query("select c from Comment c left join fetch c.schedule left join fetch c.user")
    List<Comment> findAll();

    // update나 delete는 @Query를 사용하는 경우 @Modifying을 붙여야 한다.
    @Modifying
    @Query("delete from Comment c where c.user.id=:id")
    void deleteByUserId(@Param("id") Long id);

    void deleteByScheduleId(Long id);

    @Modifying
    @Query("delete from Comment c where c.schedule.id in (select s.id from Schedule s where s.user.id=:id)")
    void deleteCommentWithInQuery(@Param("id") Long id);
}
