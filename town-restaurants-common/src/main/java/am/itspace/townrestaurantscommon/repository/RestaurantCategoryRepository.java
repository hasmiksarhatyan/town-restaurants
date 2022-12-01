package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.entity.RestaurantCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantCategoryRepository extends JpaRepository<RestaurantCategory, Integer> {

    boolean existsByName(String name);
}
