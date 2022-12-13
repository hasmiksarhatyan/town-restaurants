package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.entity.RestaurantCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RestaurantCategoryRepository extends JpaRepository<RestaurantCategory, Integer>, PagingAndSortingRepository<RestaurantCategory, Integer> {

    boolean existsByName(String name);
}
