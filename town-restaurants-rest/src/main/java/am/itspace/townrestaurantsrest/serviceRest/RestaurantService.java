package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.dto.FileDto;
import am.itspace.townrestaurantscommon.dto.restaurant.CreateRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.EditRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;

import java.util.List;

public interface RestaurantService {

    void delete(int id);

    List<RestaurantOverview> getAll();

    RestaurantOverview getById(int id);

    byte[] getRestaurantImage(String fileName);

    RestaurantOverview update(int id, EditRestaurantDto editCategoryDto);

    RestaurantOverview save(CreateRestaurantDto createCategoryDto, FileDto fileDto);

    List<RestaurantOverview> getRestaurantsByUser(int pageNo, int pageSize, String sortBy, String sortDir);

    List<RestaurantOverview> getAllRestaurants(int pageNo, int pageSize, String sortBy, String sortDir);
}

