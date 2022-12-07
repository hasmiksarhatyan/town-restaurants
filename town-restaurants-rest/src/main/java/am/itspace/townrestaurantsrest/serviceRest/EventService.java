package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.dto.event.CreateEventDto;
import am.itspace.townrestaurantscommon.dto.event.EditEventDto;
import am.itspace.townrestaurantscommon.dto.event.EventOverview;
import am.itspace.townrestaurantscommon.dto.fetchRequest.FetchRequestDto;
import am.itspace.townrestaurantscommon.entity.Event;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;

import java.util.List;

public interface EventService {

    void delete(int id);

    List<EventOverview> getAll();

    EventOverview save(CreateEventDto createEventDto);

    List<EventOverview> findEventsByRestaurantId(int id);

    EventOverview update(int id, EditEventDto editEventDto);

    EventOverview getById(int id) throws EntityNotFoundException;

    List<Event> getEventsList(FetchRequestDto fetchRequestDto);
}

