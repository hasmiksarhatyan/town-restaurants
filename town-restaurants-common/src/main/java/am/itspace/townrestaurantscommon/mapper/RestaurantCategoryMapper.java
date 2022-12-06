package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.restaurantCategory.CreateRestaurantCategoryDto;
import am.itspace.townrestaurantscommon.dto.restaurantCategory.RestaurantCategoryOverview;
import am.itspace.townrestaurantscommon.entity.RestaurantCategory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantCategoryMapper {

    RestaurantCategory mapToEntity(CreateRestaurantCategoryDto createRestaurantCategoryDto);

    RestaurantCategoryOverview mapToOverview(RestaurantCategory restaurantCategory);

    List<RestaurantCategoryOverview> mapToOverviewList(List<RestaurantCategory> restaurantCategory);
}
