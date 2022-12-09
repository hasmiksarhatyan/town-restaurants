package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantscommon.entity.Restaurant;
import am.itspace.townrestaurantscommon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface RestaurantRepository extends JpaRepository<Restaurant, Integer>, PagingAndSortingRepository<Restaurant, Integer> {

    boolean existsByName(String name);

    Restaurant findByEmail(String email);

    boolean existsByEmailIgnoreCase(String email);

    Page<Restaurant> findRestaurantsByUser(User user, Pageable pageable);

    List<Restaurant> findRestaurantsByUserId(int id);

    @Query("select email from Restaurant email where email=:email")
    Page<Restaurant> findByRestaurantEmail(@Param("email") String email, Pageable pageReq);

    default Page<Restaurant> findByRestaurantEmail(RestaurantOverview restaurantOverview, Pageable pageReq) {
        return findByRestaurantEmail(restaurantOverview.getEmail(), pageReq);
    }
}
