package am.itspace.townrestaurantsweb.serviceWeb;

import am.itspace.townrestaurantscommon.dto.restaurantCategory.CreateRestaurantCategoryDto;
import am.itspace.townrestaurantscommon.dto.restaurantCategory.RestaurantCategoryOverview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RestaurantCategoryService {

    Page<RestaurantCategoryOverview> findAll(Pageable pageable);

    List<RestaurantCategoryOverview> findAll();

    void addRestaurantCategory(CreateRestaurantCategoryDto dto);

    void deleteRestaurantCategory(int id);
}
