package kg.school.restschool.repositories;

import kg.school.restschool.entity.Group;
import kg.school.restschool.entity.Timetable;
import kg.school.restschool.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TimeTableRepository extends JpaRepository<Timetable, Long> {
    @Override
    Optional<Timetable> findById(Long aLong);

    Optional<Timetable> findByGroup(Group group);
}
