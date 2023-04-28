package kg.school.restschool.repositories;

import kg.school.restschool.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group,Long> {
    Optional<Group> findById(Long id);
    List<Group> findAll();
    Optional<Group> findGroupsByName(String name);

    Optional<Group> deleteGroupByName(String name);
}