package kg.school.restschool.repositories;

import kg.school.restschool.entity.Mark;
import kg.school.restschool.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {
    List<Mark> getMarkByUserId(Long id);
    List<Mark> getAllByUser(User user);
}
