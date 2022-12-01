package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.dto.event.CreateEventDto;
import am.itspace.townrestaurantscommon.dto.event.EditEventDto;
import am.itspace.townrestaurantscommon.dto.event.EventOverview;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;

import java.util.List;

public interface EventService {

    List<EventOverview> getAll();

    EventOverview getById(int id) throws EntityNotFoundException;

    EventOverview save(CreateEventDto createEventDto);

    EventOverview update(int id, EditEventDto editEventDto);

    void delete(int id);
}

