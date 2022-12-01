package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.event.CreateEventDto;
import am.itspace.townrestaurantscommon.dto.event.EventOverview;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantscommon.entity.Event;
import am.itspace.townrestaurantscommon.entity.Restaurant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.4.1 (Amazon.com Inc.)"
)
@Component
public class EventMapperImpl implements EventMapper {

    @Override
    public Event mapToEntity(CreateEventDto eventDto) {
        if ( eventDto == null ) {
            return null;
        }

        Event.EventBuilder event = Event.builder();

        event.restaurant( createEventDtoToRestaurant( eventDto ) );
        event.name( eventDto.getName() );
        event.description( eventDto.getDescription() );
        event.price( eventDto.getPrice() );
        if ( eventDto.getEventDateTime() != null ) {
            event.eventDateTime( LocalDateTime.parse( eventDto.getEventDateTime() ) );
        }
        List<String> list = eventDto.getPictures();
        if ( list != null ) {
            event.pictures( new ArrayList<String>( list ) );
        }

        return event.build();
    }

    @Override
    public EventOverview mapToOverview(Event event) {
        if ( event == null ) {
            return null;
        }

        EventOverview.EventOverviewBuilder eventOverview = EventOverview.builder();

        eventOverview.restaurantOverview( restaurantToRestaurantOverview( event.getRestaurant() ) );
        if ( event.getId() != null ) {
            eventOverview.id( event.getId() );
        }
        eventOverview.name( event.getName() );
        eventOverview.description( event.getDescription() );
        eventOverview.price( event.getPrice() );
        eventOverview.eventDateTime( event.getEventDateTime() );
        List<String> list = event.getPictures();
        if ( list != null ) {
            eventOverview.pictures( new ArrayList<String>( list ) );
        }

        return eventOverview.build();
    }

    @Override
    public List<EventOverview> mapToOverviewList(List<Event> events) {
        if ( events == null ) {
            return null;
        }

        List<EventOverview> list = new ArrayList<EventOverview>( events.size() );
        for ( Event event : events ) {
            list.add( mapToOverview( event ) );
        }

        return list;
    }

    protected Restaurant createEventDtoToRestaurant(CreateEventDto createEventDto) {
        if ( createEventDto == null ) {
            return null;
        }

        Restaurant.RestaurantBuilder restaurant = Restaurant.builder();

        if ( createEventDto.getRestaurantId() != null ) {
            restaurant.id( createEventDto.getRestaurantId() );
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
}
