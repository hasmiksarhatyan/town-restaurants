package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.dto.FetchRequestDto;
import am.itspace.townrestaurantscommon.dto.restaurantCategory.CreateRestaurantCategoryDto;
import am.itspace.townrestaurantscommon.dto.restaurantCategory.EditRestaurantCategoryDto;
import am.itspace.townrestaurantscommon.dto.restaurantCategory.RestaurantCategoryOverview;
import am.itspace.townrestaurantscommon.entity.RestaurantCategory;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;

import java.util.List;

public interface RestaurantCategoryService {

    void delete(int id);

    List<RestaurantCategoryOverview> getAll();

    RestaurantCategoryOverview getById(int id) throws EntityNotFoundException;

    RestaurantCategoryOverview save(CreateRestaurantCategoryDto createRestaurantCategoryDto);

    RestaurantCategoryOverview update(int id, EditRestaurantCategoryDto editRestaurantCategoryDto);

    List<RestaurantCategory> getCategoriesList(FetchRequestDto fetchRequestDto);
}

