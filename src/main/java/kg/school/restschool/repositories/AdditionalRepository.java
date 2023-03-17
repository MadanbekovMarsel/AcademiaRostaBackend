package kg.school.restschool.repositories;

import kg.school.restschool.entity.Additional;
import kg.school.restschool.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AdditionalRepository extends JpaRepository<Additional,Long> {
    Optional<Additional> getAdditionalById(Long id);
    Optional<Additional> getAdditionalByUser(User user);
}
