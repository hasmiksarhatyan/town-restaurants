package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.dto.restaurant.EditRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantRequestDto;

import java.util.List;

public interface RestaurantService {

    void delete(int id);

    RestaurantOverview getById(int id);

    byte[] getRestaurantImage(String fileName);

    RestaurantOverview save(RestaurantRequestDto restaurantRequestDto);

    RestaurantOverview update(int id, EditRestaurantDto editCategoryDto);

    List<RestaurantOverview> getAll(int pageNo, int pageSize, String sortBy, String sortDir);

    List<RestaurantOverview> getRestaurantsByUser(int pageNo, int pageSize, String sortBy, String sortDir);
}

