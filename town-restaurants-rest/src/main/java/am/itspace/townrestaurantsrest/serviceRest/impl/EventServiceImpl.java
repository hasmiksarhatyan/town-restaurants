package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.event.CreateEventDto;
import am.itspace.townrestaurantscommon.dto.event.EditEventDto;
import am.itspace.townrestaurantscommon.dto.event.EventOverview;
import am.itspace.townrestaurantscommon.entity.Event;
import am.itspace.townrestaurantscommon.mapper.EventMapper;
import am.itspace.townrestaurantscommon.repository.EventRepository;
import am.itspace.townrestaurantsrest.exception.EntityAlreadyExistsException;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;
import am.itspace.townrestaurantsrest.exception.Error;
import am.itspace.townrestaurantsrest.serviceRest.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public EventOverview save(CreateEventDto createEventDto) {
        if (eventRepository.existsByName(createEventDto.getName())) {
            log.info("Event with that name already exists {}", createEventDto.getName());
            throw new EntityAlreadyExistsException(Error.EVENT_ALREADY_EXISTS);
        }
        log.info("The event was successfully stored in the database {}", createEventDto.getName());
        return eventMapper.mapToOverview(eventRepository.save(eventMapper.mapToEntity(createEventDto)));
    }

    @Override
    public List<EventOverview> getAll() {
        List<Event> events = eventRepository.findAll();
        if (events.isEmpty()) {
            log.info("Event not found");
            throw new EntityNotFoundException(Error.EVENT_NOT_FOUND);
        }
        log.info("Event successfully detected");
        return eventMapper.mapToOverviewList(events);
    }

    @Override
    public EventOverview getById(int id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Error.EVENT_NOT_FOUND));
        log.info("Event successfully found {}", event.getName());
        return eventMapper.mapToOverview(event);
    }

    @Override
    public EventOverview update(int id, EditEventDto editEventDto) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Error.EVENT_NOT_FOUND));
        log.info("Event with that id not found");
        if (editEventDto.getName() != null) {
            event.setName(editEventDto.getName());
            eventRepository.save(event);
        }
        log.info("The event was successfully stored in the database {}", event.getName());
        return eventMapper.mapToOverview(event);
    }

    @Override
    public void delete(int id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            log.info("The event has been successfully deleted");
        } else {
            log.info("Event not found");
            throw new EntityNotFoundException(Error.EVENT_NOT_FOUND);
        }
    }
}
