package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.restaurant.CreateRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantscommon.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    @Mapping(source = "dto.restaurantCategoryId", target = "restaurantCategory.id")
    Restaurant mapToEntity(CreateRestaurantDto dto);

    @Mapping(source = "restaurant.restaurantCategory", target = "restaurantCategoryOverview")
    @Mapping(source = "restaurant.user", target = "userOverview")
    RestaurantOverview mapToResponseDto(Restaurant restaurant);

    List<RestaurantOverview> mapToResponseDtoList(List<Restaurant> restaurants);
}

