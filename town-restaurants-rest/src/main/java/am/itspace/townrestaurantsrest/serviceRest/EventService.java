package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.dto.FileDto;
import am.itspace.townrestaurantscommon.dto.event.CreateEventDto;
import am.itspace.townrestaurantscommon.dto.event.EditEventDto;
import am.itspace.townrestaurantscommon.dto.event.EventOverview;
import am.itspace.townrestaurantscommon.dto.FetchRequestDto;
import am.itspace.townrestaurantscommon.entity.Event;

import java.util.List;
import java.util.Map;

public interface EventService {

    void delete(int id);

    List<EventOverview> getAll();

    EventOverview getById(int id);

    byte[] getEventImage(String fileName);

    EventOverview save(CreateEventDto createEventDto, FileDto fileDto);

    List<EventOverview> findEventsByRestaurantId(int id);

    EventOverview update(int id, EditEventDto editEventDto);

    Map<Integer, List<EventOverview>> sortEventsByRestaurant();

    List<Event> getEventsList(FetchRequestDto fetchRequestDto);
}

