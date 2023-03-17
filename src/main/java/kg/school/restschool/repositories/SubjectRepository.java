package kg.school.restschool.repositories;

import kg.school.restschool.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findSubjectByName(String name);

    List<Subject> findAll();
    Optional<Subject> findSubjectById(Long subjectId);
}
