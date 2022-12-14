package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.FileDto;
import am.itspace.townrestaurantscommon.dto.event.CreateEventDto;
import am.itspace.townrestaurantscommon.dto.event.EditEventDto;
import am.itspace.townrestaurantscommon.dto.event.EventOverview;
import am.itspace.townrestaurantscommon.dto.event.EventRequestDto;
import am.itspace.townrestaurantscommon.entity.Event;
import am.itspace.townrestaurantscommon.entity.Restaurant;
import am.itspace.townrestaurantscommon.mapper.EventMapper;
import am.itspace.townrestaurantscommon.repository.EventRepository;
import am.itspace.townrestaurantscommon.repository.RestaurantRepository;
import am.itspace.townrestaurantscommon.utilCommon.FileUtil;
import am.itspace.townrestaurantsrest.exception.Error;
import am.itspace.townrestaurantsrest.exception.*;
import am.itspace.townrestaurantsrest.serviceRest.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static am.itspace.townrestaurantsrest.exception.Error.FILE_NOT_FOUND;
import static am.itspace.townrestaurantsrest.exception.Error.FILE_UPLOAD_FAILED;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final FileUtil fileUtil;
    private final EventMapper eventMapper;
    private final EventRepository eventRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public EventOverview save(EventRequestDto dto) {
        CreateEventDto createEventDto = dto.getCreateEventDto();
        FileDto fileDto = dto.getFileDto();
        if (eventRepository.existsByName(createEventDto.getName())) {
            log.info("Event with that name already exists {}", createEventDto.getName());
            throw new EntityAlreadyExistsException(Error.EVENT_ALREADY_EXISTS);
        }
        if (fileDto.getFiles() != null) {
            try {
                MultipartFile[] files = fileDto.getFiles();
                for (MultipartFile file : files) {
                    if (!file.isEmpty() && file.getSize() > 0) {
                        if (file.getContentType() != null && !file.getContentType().contains("image")) {
                            throw new MyFileNotFoundException(FILE_NOT_FOUND);
                        }
                    }
                }
                createEventDto.setPictures(fileUtil.uploadImages(files));
            } catch (IOException e) {
                throw new FileStorageException(FILE_UPLOAD_FAILED);
            }
        }
        log.info("The event was successfully stored in the database {}", createEventDto.getName());
        return eventMapper.mapToOverview(eventRepository.save(eventMapper.mapToEntity(createEventDto)));
    }

    @Override
    public byte[] getEventImage(String fileName) {
        try {
            log.info("Images successfully found");
            return fileUtil.getImage(fileName);
        } catch (IOException e) {
            throw new MyFileNotFoundException(FILE_NOT_FOUND);
        }
    }

    @Override
    public List<EventOverview> getAllEvents(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Event> events = eventRepository.findAll(pageable);
        return findEvent(events);
    }

    @Override
    public List<EventOverview> getEventsByRestaurantId(int id, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Event> events = eventRepository.findEventsByRestaurantId(id, pageable);
        return findEvent(events);
    }

    @Override
    public EventOverview getById(int id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Error.EVENT_NOT_FOUND));
        log.info("Event successfully found {}", event.getName());
        return eventMapper.mapToOverview(event);
    }

    @Override
    public Map<Integer, List<EventOverview>> sortEventsByRestaurant() {
        Map<Integer, List<EventOverview>> events = new HashMap<>();
        List<Restaurant> restaurants = restaurantRepository.findAll();
        if (restaurants.isEmpty()) {
            throw new EntityNotFoundException(Error.RESTAURANT_NOT_FOUND);
        }
        for (Restaurant restaurant : restaurants) {
            if (restaurant != null) {
                List<EventOverview> eventsByRestaurant = eventMapper.mapToOverviewList(eventRepository.findEventsByRestaurant_Id(restaurant.getId()));
                if (!eventsByRestaurant.isEmpty()) {
                    events.put(restaurant.getId(), eventsByRestaurant);
                }
            }
        }
        if (events.isEmpty()) {
            throw new EntityNotFoundException(Error.EVENT_NOT_FOUND);
        }
        log.info("Events successfully sorted by restaurant");
        return events;
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
            Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);
            restaurantOptional.ifPresent(event::setRestaurant);
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

    private List<EventOverview> findEvent(Page<Event> events) {
        if (events.isEmpty()) {
            log.info("Event not found");
            throw new EntityNotFoundException(Error.EVENT_NOT_FOUND);
        }
        List<Event> listOfEvents = events.getContent();
        log.info("Event successfully found");
        return new ArrayList<>(eventMapper.mapToOverviewList(listOfEvents));
    }
}
