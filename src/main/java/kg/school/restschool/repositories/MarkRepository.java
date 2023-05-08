package kg.school.restschool.repositories;

import kg.school.restschool.entity.Mark;
import kg.school.restschool.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {
    List<Mark> getMarkByUserId(Long id);
    List<Mark> getAllByUser(User user);

    Optional<Mark> getMarkByCreatedDate(Date date);

    @Query("SELECT m FROM Mark m WHERE m.user = ?1 and m.createdDate = ?2 and m.topic = ?3")
    Optional<Mark> getMarkByUserAndDateAndTopic(User user, Date date,String topic);


    @Query("SELECT m FROM Mark m WHERE m.user = ?1 and m.createdDate >=?2")
    List<Mark> getMarkByUserAndDate(User user, Date thirtyDaysAgo);

    @Query("SELECT m.user, SUM(m.correctAnswers), SUM(m.totalQuestions), m.createdDate " +
            "FROM Mark m WHERE m.createdDate >= ?2 GROUP BY m.createdDate,m.user")
    List<Mark> getMarksByUniqueDays(User user, Date date);

}
