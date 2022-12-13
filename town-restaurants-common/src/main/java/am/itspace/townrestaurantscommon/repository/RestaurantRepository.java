package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.entity.Restaurant;
import am.itspace.townrestaurantscommon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface RestaurantRepository extends JpaRepository<Restaurant, Integer>, PagingAndSortingRepository<Restaurant, Integer> {

    boolean existsByName(String name);

    Restaurant findByEmail(String email);

    boolean existsByEmailIgnoreCase(String email);

    List<Restaurant> findRestaurantsByUserId(int id);

    Page<Restaurant> findRestaurantsByUser(User user, Pageable pageable);
}
