package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.restaurantCategory.CreateRestaurantCategoryDto;
import am.itspace.townrestaurantscommon.dto.restaurantCategory.RestaurantCategoryOverview;
import am.itspace.townrestaurantscommon.entity.RestaurantCategory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantCategoryMapper2 {

     RestaurantCategory mapToEntity(CreateRestaurantCategoryDto createRestaurantCategoryDto);

    RestaurantCategoryOverview mapToResponseDto(RestaurantCategory restaurantCategory);

    List<RestaurantCategoryOverview> mapToResponseDtoList(List<RestaurantCategory> restaurantCategory);
}
