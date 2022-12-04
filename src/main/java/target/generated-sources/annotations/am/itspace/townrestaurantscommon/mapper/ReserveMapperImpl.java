package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.reserve.CreateReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.ReserveOverview;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantscommon.dto.user.UserOverview;
import am.itspace.townrestaurantscommon.entity.Reserve;
import am.itspace.townrestaurantscommon.entity.Restaurant;
import am.itspace.townrestaurantscommon.entity.User;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.4.1 (Amazon.com Inc.)"
)
@Component
public class ReserveMapperImpl implements ReserveMapper {

    @Override
    public Reserve mapToEntity(CreateReserveDto dto) {
        if ( dto == null ) {
            return null;
        }

        Reserve.ReserveBuilder reserve = Reserve.builder();

        reserve.restaurant( createReserveDtoToRestaurant( dto ) );
        if ( dto.getReservedDate() != null ) {
            reserve.reservedDate( LocalDate.parse( dto.getReservedDate() ) );
        }
        if ( dto.getReservedTime() != null ) {
            reserve.reservedTime( LocalTime.parse( dto.getReservedTime() ) );
        }
        reserve.peopleCount( dto.getPeopleCount() );
        reserve.phoneNumber( dto.getPhoneNumber() );

        return reserve.build();
    }

    @Override
    public ReserveOverview mapToOverview(Reserve reserve) {
        if ( reserve == null ) {
            return null;
        }

        ReserveOverview.ReserveOverviewBuilder reserveOverview = ReserveOverview.builder();

        reserveOverview.restaurantOverview( restaurantToRestaurantOverview( reserve.getRestaurant() ) );
        reserveOverview.userOverview( userToUserOverview( reserve.getUser() ) );
        reserveOverview.id( reserve.getId() );
        reserveOverview.reservedAt( reserve.getReservedAt() );
        reserveOverview.reservedDate( reserve.getReservedDate() );
        reserveOverview.reservedTime( reserve.getReservedTime() );
        reserveOverview.peopleCount( reserve.getPeopleCount() );
        reserveOverview.phoneNumber( reserve.getPhoneNumber() );
        if ( reserve.getStatus() != null ) {
            reserveOverview.status( reserve.getStatus().name() );
        }

        return reserveOverview.build();
    }

    @Override
    public List<ReserveOverview> mapToOverviewList(List<Reserve> reserves) {
        if ( reserves == null ) {
            return null;
        }

        List<ReserveOverview> list = new ArrayList<ReserveOverview>( reserves.size() );
        for ( Reserve reserve : reserves ) {
            list.add( mapToOverview( reserve ) );
        }

        return list;
    }

    protected Restaurant createReserveDtoToRestaurant(CreateReserveDto createReserveDto) {
        if ( createReserveDto == null ) {
            return null;
        }

        Restaurant.RestaurantBuilder restaurant = Restaurant.builder();

        if ( createReserveDto.getRestaurantId() != null ) {
            restaurant.id( createReserveDto.getRestaurantId() );
        }

        return restaurant.build();
    }

    protected RestaurantOverview restaurantToRestaurantOverview(Restaurant restaurant) {
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

    protected UserOverview userToUserOverview(User user) {
        if ( user == null ) {
            return null;
        }

        UserOverview.UserOverviewBuilder userOverview = UserOverview.builder();

        userOverview.id( user.getId() );
        userOverview.firstName( user.getFirstName() );
        userOverview.lastName( user.getLastName() );
        userOverview.email( user.getEmail() );
        userOverview.role( user.getRole() );

        return userOverview.build();
    }
}
