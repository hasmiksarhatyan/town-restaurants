package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.dto.restaurantCategory.RestaurantCategoryOverview;
import am.itspace.townrestaurantscommon.entity.RestaurantCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface RestaurantCategoryRepository extends JpaRepository<RestaurantCategory, Integer>, PagingAndSortingRepository<RestaurantCategory, Integer> {

    boolean existsByName(String name);

    @Query("select name from RestaurantCategory name where name=:name")
    Page<RestaurantCategory> findByName(@Param("name") String name, Pageable pageReq);

    default Page<RestaurantCategory> findByName(RestaurantCategoryOverview restaurantCategoryOverview, Pageable pageReq) {
        return findByName(restaurantCategoryOverview.getName(), pageReq);
    }
}
