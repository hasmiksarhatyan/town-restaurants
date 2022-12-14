package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.dto.event.EditEventDto;
import am.itspace.townrestaurantscommon.dto.event.EventOverview;
import am.itspace.townrestaurantscommon.dto.event.EventRequestDto;

import java.util.List;
import java.util.Map;

public interface EventService {

    void delete(int id);

    EventOverview getById(int id);

    byte[] getEventImage(String fileName);

    EventOverview save(EventRequestDto eventRequestDto);

    EventOverview update(int id, EditEventDto editEventDto);

    Map<Integer, List<EventOverview>> sortEventsByRestaurant();

    List<EventOverview> getAllEvents(int pageNo, int pageSize, String sortBy, String sortDir);

    List<EventOverview> getEventsByRestaurantId(int id,int pageNo, int pageSize, String sortBy, String sortDir);
}

