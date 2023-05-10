package kg.school.restschool.repositories;

import kg.school.restschool.entity.Mark;
import kg.school.restschool.entity.User;
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

    @Query("SELECT SUM(m.correctAnswers), SUM(m.totalQuestions), m.createdDate " +
            "FROM Mark m WHERE m.user = ?1 and m.createdDate >= ?2 GROUP BY m.createdDate,m.user ORDER BY m.createdDate")
    List<Object[]> getMarksByUniqueDays(User user, Date date);

    @Query("SELECT SUM(m.correctAnswers),SUM(m.totalQuestions), m.topic " +
            "FROM Mark m WHERE m.user = ?1 and m.createdDate >= ?2 GROUP BY m.topic, m.user")
    List<Object[]> getMarksByUniqueTopics(User user, Date date);

}
