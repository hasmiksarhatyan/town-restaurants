package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.dto.user.UserOverview;
import am.itspace.townrestaurantscommon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>, PagingAndSortingRepository<User, Integer> {

    boolean existsByEmailIgnoreCase(String email);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("select email from User email where email=:email")
    Page<User> findByEmail(@Param("email") String email, Pageable pageReq);

    default Page<User> findByEmail(UserOverview userOverview, Pageable pageReq) {
        return findByEmail(userOverview.getEmail(), pageReq);
    }
}
