package kg.school.restschool.repositories;

import kg.school.restschool.entity.User;
import kg.school.restschool.entity.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
    Optional<User> getUserById(Long id);

    List<User> getUsersByRole(ERole role);

}
