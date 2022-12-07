package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.event.CreateEventDto;
import am.itspace.townrestaurantscommon.dto.event.EditEventDto;
import am.itspace.townrestaurantscommon.dto.event.EventOverview;
import am.itspace.townrestaurantscommon.dto.fetchRequest.FetchRequestDto;
import am.itspace.townrestaurantscommon.entity.Event;
import am.itspace.townrestaurantscommon.mapper.EventMapper;
import am.itspace.townrestaurantscommon.repository.EventRepository;
import am.itspace.townrestaurantscommon.repository.RestaurantRepository;
import am.itspace.townrestaurantsrest.exception.EntityAlreadyExistsException;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;
import am.itspace.townrestaurantsrest.exception.Error;
import am.itspace.townrestaurantsrest.serviceRest.EventService;
import am.itspace.townrestaurantsrest.serviceRest.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventMapper eventMapper;
    private final EventRepository eventRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserService userService;

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
    public List<Event> getEventsList(FetchRequestDto dto) {
        PageRequest pageReq = PageRequest.of(dto.getPage(), dto.getSize(), Sort.Direction.fromString(dto.getSortDir()), dto.getSort());
        Page<Event> events = eventRepository.findByEventName(dto.getInstance(), pageReq);
        if (events.isEmpty()) {
            log.info("Event not found");
            throw new EntityNotFoundException(Error.EVENT_NOT_FOUND);
        }
        return events.getContent();
    }

    @Override
    public EventOverview getById(int id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Error.EVENT_NOT_FOUND));
        log.info("Event successfully found {}", event.getName());
        return eventMapper.mapToOverview(event);
    }

    @Override
    public List<EventOverview> findEventsByRestaurantId(int id) {
        List<Event> events = eventRepository.findEventsByRestaurant_Id(id);
        if (events.isEmpty()) {
            log.info("Event not found");
            throw new EntityNotFoundException(Error.EVENT_NOT_FOUND);
        } else {
            log.info("Event successfully detected");
            return eventMapper.mapToOverviewList(events);
        }
    }

    @Override
    public EventOverview update(int id, EditEventDto editEventDto) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Error.EVENT_NOT_FOUND));
        log.info("Event with that id not found");
        String name = editEventDto.getName();
        if (StringUtils.hasText(name)) {
            event.setName(name);
        }
        String description = editEventDto.getDescription();
        if (StringUtils.hasText(description)) {
            event.setDescription(description);
        }
        double price = editEventDto.getPrice();
        if (price >= 0) {
            event.setPrice(price);
        }
        String eventDateTime = editEventDto.getEventDateTime();
        if (eventDateTime != null) {
            event.setEventDateTime(LocalDateTime.parse(eventDateTime));
        }
        Integer restaurantId = editEventDto.getRestaurantId();
        if (restaurantId != null) {
            event.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        }
        eventRepository.save(event);
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
