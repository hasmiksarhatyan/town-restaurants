package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.restaurant.CreateRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantscommon.entity.Restaurant;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.4.1 (Amazon.com Inc.)"
)
@Component
public class RestaurantMapper2Impl implements RestaurantMapper2 {

    @Override
    public Restaurant mapToEntity(CreateRestaurantDto createRestaurantDto) {
        if ( createRestaurantDto == null ) {
            return null;
        }

        Restaurant.RestaurantBuilder restaurant = Restaurant.builder();

        restaurant.name( createRestaurantDto.getName() );
        restaurant.address( createRestaurantDto.getAddress() );
        restaurant.email( createRestaurantDto.getEmail() );
        restaurant.phone( createRestaurantDto.getPhone() );
        restaurant.deliveryPrice( createRestaurantDto.getDeliveryPrice() );
        List<String> list = createRestaurantDto.getPictures();
        if ( list != null ) {
            restaurant.pictures( new ArrayList<String>( list ) );
        }

        return restaurant.build();
    }

    @Override
    public RestaurantOverview mapToResponseDto(Restaurant restaurant) {
        if ( restaurant == null ) {
            return null;
        }

        RestaurantOverview.RestaurantOverviewBuilder restaurantOverview = RestaurantOverview.builder();

        restaurantOverview.id( restaurant.getId() );
        restaurantOverview.name( restaurant.getName() );
        restaurantOverview.address( restaurant.getAddress() );
        restaurantOverview.email( restaurant.getEmail() );
        restaurantOverview.phone( restaurant.getPhone() );
        restaurantOverview.deliveryPrice( restaurant.getDeliveryPrice() );
        List<String> list = restaurant.getPictures();
        if ( list != null ) {
            restaurantOverview.pictures( new ArrayList<String>( list ) );
        }
        restaurantOverview.user( restaurant.getUser() );

        return restaurantOverview.build();
    }

    @Override
    public List<RestaurantOverview> mapToResponseDtoList(List<Restaurant> restaurants) {
        if ( restaurants == null ) {
            return null;
        }

        List<RestaurantOverview> list = new ArrayList<RestaurantOverview>( restaurants.size() );
        for ( Restaurant restaurant : restaurants ) {
            list.add( mapToResponseDto( restaurant ) );
        }

        return list;
    }
}
