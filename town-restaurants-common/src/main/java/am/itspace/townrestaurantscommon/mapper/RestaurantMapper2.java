package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.restaurant.CreateRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantscommon.entity.Restaurant;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMapper2 {

     Restaurant mapToEntity(CreateRestaurantDto createRestaurantDto);

    RestaurantOverview mapToResponseDto(Restaurant restaurant);

    List<RestaurantOverview> mapToResponseDtoList(List<Restaurant> restaurants);
}
