package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.entity.Restaurant;
import am.itspace.townrestaurantscommon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    boolean existsByEmailIgnoreCase(String email);

    Page<Restaurant> findRestaurantsByUser(User user, Pageable pageable);

    Optional<Restaurant> findRestaurantsByUser(User user);

    List<Restaurant> findRestaurantsByUserId(User user);

    boolean existsByName(String name);
}
