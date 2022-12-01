package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmailIgnoreCase(String email);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
