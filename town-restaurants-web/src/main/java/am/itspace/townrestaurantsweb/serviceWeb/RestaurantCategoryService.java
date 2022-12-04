package am.itspace.townrestaurantsweb.serviceWeb;

import am.itspace.townrestaurantscommon.dto.restaurantCategory.CreateRestaurantCategoryDto;
import am.itspace.townrestaurantscommon.dto.restaurantCategory.RestaurantCategoryOverview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RestaurantCategoryService {

    void deleteRestaurantCategory(int id);

    List<RestaurantCategoryOverview> findAll();

    Page<RestaurantCategoryOverview> findAll(Pageable pageable);

    void addRestaurantCategory(CreateRestaurantCategoryDto dto);
}
