package kg.school.restschool.repositories;

import kg.school.restschool.entity.Mark;
import kg.school.restschool.entity.User;
import kg.school.restschool.entity.enums.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Observable;
import java.util.Optional;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {
    List<Mark> getMarkByUserId(Long id);
    List<Mark> getAllByUser(User user);

    Optional<Mark> getMarkByCreatedDate(Date date);

    @Query("SELECT m FROM Mark m WHERE m.user = ?1 and m.createdDate = ?2 and m.topic = ?3")
    Optional<Mark> getMarkByUserAndDate(User user, Date date,Topic topic);
}
