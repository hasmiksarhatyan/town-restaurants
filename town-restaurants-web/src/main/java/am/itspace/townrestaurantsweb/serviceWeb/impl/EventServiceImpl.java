package am.itspace.townrestaurantsweb.serviceWeb.impl;

import am.itspace.townrestaurantscommon.dto.event.CreateEventDto;
import am.itspace.townrestaurantscommon.dto.event.EditEventDto;
import am.itspace.townrestaurantscommon.dto.event.EventOverview;
import am.itspace.townrestaurantscommon.entity.Event;
import am.itspace.townrestaurantscommon.entity.Restaurant;
import am.itspace.townrestaurantscommon.mapper.EventMapper;
import am.itspace.townrestaurantscommon.repository.EventRepository;
import am.itspace.townrestaurantscommon.repository.RestaurantRepository;
import am.itspace.townrestaurantsweb.serviceWeb.EventService;
import am.itspace.townrestaurantsweb.utilWeb.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final FileUtil fileUtil;
    private final EventMapper eventMapper;
    private final EventRepository eventRepository;
    private final RestaurantRepository restaurantRepository;

    public Page<EventOverview> findAll(Pageable pageable) {
        log.info("Event successfully found");
        return eventRepository.findAll(pageable).map(eventMapper::mapToOverview);
    }

    public Page<EventOverview> findEventsByRestaurantId(int id, Pageable pageable) {
        log.info("Events successfully found by restaurantId");
        return eventRepository.findEventsByRestaurantId(id, pageable).map(eventMapper::mapToOverview);
    }

    public Map<Integer, List<EventOverview>> sortEventsByRestaurant() {
        Map<Integer, List<EventOverview>> events = new HashMap<>();
        List<Restaurant> all = restaurantRepository.findAll();
        for (Restaurant restaurant : all) {
            if (restaurant != null) {
                List<EventOverview> eventsByRestaurant = eventMapper.mapToOverviewList(eventRepository.findEventsByRestaurant_Id(restaurant.getId()));
                events.put(restaurant.getId(), eventsByRestaurant);
            }
        }
        log.info("Events successfully sorted by restaurant");
        return events;
    }

    @Override
    public void save(CreateEventDto dto, MultipartFile[] files) throws IOException {
        dto.setPictures(fileUtil.uploadImages(files));
        eventRepository.save(eventMapper.mapToEntity(dto));
        log.info("The event was successfully stored in the database {}", dto.getName());
    }

    @Override
    public byte[] getEventImage(String fileName) throws IOException {
        log.info("Images successfully found");
        return fileUtil.getImage(fileName);
    }

    @Override
    public void editEvent(EditEventDto dto, int id, MultipartFile[] files) throws IOException {
        Event event = eventRepository.findById(id).orElseThrow(IllegalStateException::new);
        String name = dto.getName();
        if (StringUtils.hasText(name)) {
            event.setName(name);
        }
        String description = dto.getDescription();
        if (StringUtils.hasText(description)) {
            event.setDescription(description);
        }
        double price = dto.getPrice();
        if (price >= 0) {
            event.setPrice(price);
        }
        String eventDateTime = dto.getEventDateTime();
        if (eventDateTime != null) {
            event.setEventDateTime(LocalDateTime.parse(eventDateTime));
        }
        Integer restaurantId = dto.getRestaurantId();
        if (restaurantId != null) {
            event.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        }
        List<String> pictures = dto.getPictures();
        if (pictures != event.getPictures()) {
            event.setPictures(fileUtil.uploadImages(files));
        }
        log.info("The event was successfully stored in the database {}", event.getName());
        eventRepository.save(event);
    }

    @Override
    public void deleteEvent(int id) {
        if (!eventRepository.existsById(id)) {
            log.info("Event not found");
            throw new IllegalStateException();
        }
        log.info("The event has been successfully deleted");
        eventRepository.deleteById(id);
    }

    @Override
    public EventOverview findById(int id) {
        Event event = eventRepository.findById(id).orElseThrow(IllegalStateException::new);
        log.info("Event successfully found");
        return eventMapper.mapToOverview(event);
    }
}

