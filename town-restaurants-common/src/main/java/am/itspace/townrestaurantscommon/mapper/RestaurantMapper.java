package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.ImageOverview;
import am.itspace.townrestaurantscommon.dto.restaurant.CreateRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantscommon.entity.Restaurant;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    //    @Mapping(target = "restaurant", ignore = true)
//    @Mapping(source = "restaurantCategoryId", target = "restaurantCategory.id")
    Restaurant mapToEntity(CreateRestaurantDto createRestaurantDto);

    //    @Mapping(source = "restaurant.restaurantCategory",target = "restaurantCategoryOverview")
    RestaurantOverview mapToResponseDto(Restaurant restaurant);

    List<RestaurantOverview> mapToResponseDtoList(List<Restaurant> restaurants);
//
//    List<RestaurantOverview> mapToResponseList(Page<Restaurant> restaurants);
//
//    ImageOverview mapToUserImageOverview(Restaurant restaurant);
}

