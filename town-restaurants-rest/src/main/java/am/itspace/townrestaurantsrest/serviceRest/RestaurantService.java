package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.dto.FileDto;
import am.itspace.townrestaurantscommon.dto.FetchRequestDto;
import am.itspace.townrestaurantscommon.dto.restaurant.CreateRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.EditRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantscommon.entity.Restaurant;

import java.util.List;

public interface RestaurantService {

    void delete(int id);

    List<RestaurantOverview> getAll();

    List<Restaurant> getRestaurantsByUser(FetchRequestDto dto);

    byte[] getRestaurantImage(String fileName);

    RestaurantOverview getById(int id);

    RestaurantOverview update(int id, EditRestaurantDto editCategoryDto);

    List<Restaurant> getRestaurantsList(FetchRequestDto fetchRequestDto);

    RestaurantOverview save(CreateRestaurantDto createCategoryDto, FileDto fileDto);
}

