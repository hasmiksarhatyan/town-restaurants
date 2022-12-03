package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.dto.event.EventOverview;
import am.itspace.townrestaurantscommon.dto.restaurant.CreateRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.EditRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;

import java.util.List;

public interface RestaurantService {

    List<RestaurantOverview> getAll();

    RestaurantOverview getById(int id) throws EntityNotFoundException;

    RestaurantOverview save(CreateRestaurantDto createCategoryDto);

    RestaurantOverview update(int id, EditRestaurantDto editCategoryDto);

    void delete(int id);

    List<EventOverview> getByEventId(int id);
}

