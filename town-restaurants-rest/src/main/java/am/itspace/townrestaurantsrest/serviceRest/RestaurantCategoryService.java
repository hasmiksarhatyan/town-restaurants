package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.dto.restaurantCategory.CreateRestaurantCategoryDto;
import am.itspace.townrestaurantscommon.dto.restaurantCategory.EditRestaurantCategoryDto;
import am.itspace.townrestaurantscommon.dto.restaurantCategory.RestaurantCategoryOverview;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;

import java.util.List;

public interface RestaurantCategoryService {

    List<RestaurantCategoryOverview> getAll();

    RestaurantCategoryOverview getById(int id) throws EntityNotFoundException;

    RestaurantCategoryOverview save(CreateRestaurantCategoryDto createRestaurantCategoryDto);

    RestaurantCategoryOverview update(int id, EditRestaurantCategoryDto editRestaurantCategoryDto);

    void delete(int id);
}

