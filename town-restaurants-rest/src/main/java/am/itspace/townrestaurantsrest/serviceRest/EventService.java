package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.dto.FileDto;
import am.itspace.townrestaurantscommon.dto.event.CreateEventDto;
import am.itspace.townrestaurantscommon.dto.event.EditEventDto;
import am.itspace.townrestaurantscommon.dto.event.EventOverview;

import java.util.List;
import java.util.Map;

public interface EventService {

    void delete(int id);

    List<EventOverview> getAll();

    EventOverview getById(int id);

    byte[] getEventImage(String fileName);

    List<EventOverview> findEventsByRestaurantId(int id);

    EventOverview update(int id, EditEventDto editEventDto);

    Map<Integer, List<EventOverview>> sortEventsByRestaurant();

    EventOverview save(CreateEventDto createEventDto, FileDto fileDto);

    List<EventOverview> getAllEvents(int pageNo, int pageSize, String sortBy, String sortDir);
}

