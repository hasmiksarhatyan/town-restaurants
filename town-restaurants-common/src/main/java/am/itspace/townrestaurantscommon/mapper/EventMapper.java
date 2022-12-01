package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.event.CreateEventDto;
import am.itspace.townrestaurantscommon.dto.event.EventOverview;
import am.itspace.townrestaurantscommon.dto.restaurant.CreateRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantscommon.entity.Event;
import am.itspace.townrestaurantscommon.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(source = "eventDto.restaurantId", target = "restaurant.id")
//    @Mapping(target = "restaurant", ignore = true)
    Event mapToEntity(CreateEventDto eventDto);

    @Mapping(source = "event.restaurant",target = "restaurantOverview")
    EventOverview mapToOverview(Event event);

    List<EventOverview> mapToOverviewList(List<Event> events);
}
