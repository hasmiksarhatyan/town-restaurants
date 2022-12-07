package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.dto.fetchRequest.FetchRequestDto;
import am.itspace.townrestaurantscommon.dto.restaurant.CreateRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.EditRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantscommon.entity.Restaurant;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;

import java.util.List;

public interface RestaurantService {

    void delete(int id);

    List<RestaurantOverview> getAll();

    RestaurantOverview save(CreateRestaurantDto createCategoryDto);

    RestaurantOverview getById(int id) throws EntityNotFoundException;

    RestaurantOverview update(int id, EditRestaurantDto editCategoryDto);

    List<Restaurant> getRestaurantsList(FetchRequestDto fetchRequestDto);

//    ImageOverview uploadImage(UUID id, MultipartFile multipartFile);
//
//    void deleteImage(UUID restId);
}

