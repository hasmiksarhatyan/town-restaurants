package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.restaurantCategory.CreateRestaurantCategoryDto;
import am.itspace.townrestaurantscommon.dto.restaurantCategory.RestaurantCategoryOverview;
import am.itspace.townrestaurantscommon.entity.RestaurantCategory;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.4.1 (Amazon.com Inc.)"
)
@Component
public class RestaurantCategoryMapper2Impl implements RestaurantCategoryMapper2 {

    @Override
    public RestaurantCategory mapToEntity(CreateRestaurantCategoryDto createRestaurantCategoryDto) {
        if ( createRestaurantCategoryDto == null ) {
            return null;
        }

        RestaurantCategory.RestaurantCategoryBuilder restaurantCategory = RestaurantCategory.builder();

        restaurantCategory.name( createRestaurantCategoryDto.getName() );

        return restaurantCategory.build();
    }

    @Override
    public RestaurantCategoryOverview mapToResponseDto(RestaurantCategory restaurantCategory) {
        if ( restaurantCategory == null ) {
            return null;
        }

        RestaurantCategoryOverview.RestaurantCategoryOverviewBuilder restaurantCategoryOverview = RestaurantCategoryOverview.builder();

        restaurantCategoryOverview.id( restaurantCategory.getId() );
        restaurantCategoryOverview.name( restaurantCategory.getName() );

        return restaurantCategoryOverview.build();
    }

    @Override
    public List<RestaurantCategoryOverview> mapToResponseDtoList(List<RestaurantCategory> restaurantCategory) {
        if ( restaurantCategory == null ) {
            return null;
        }

        List<RestaurantCategoryOverview> list = new ArrayList<RestaurantCategoryOverview>( restaurantCategory.size() );
        for ( RestaurantCategory restaurantCategory1 : restaurantCategory ) {
            list.add( mapToResponseDto( restaurantCategory1 ) );
        }

        return list;
    }
}
