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

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventMapper eventMapper;
    private final EventRepository eventRepository;
    private final RestaurantRepository restaurantRepository;

    public Page<EventOverview> findAll(Pageable pageable) {
        return eventRepository.findAll(pageable).map(eventMapper::mapToOverview);

    }

    public Page<EventOverview> findEventsByRestaurantId(int id, Pageable pageable) {
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
        return events;
    }

    @Override
    public void save(CreateEventDto dto, MultipartFile[] files) throws IOException {
        dto.setPictures(FileUtil.uploadImages(files));
        eventRepository.save(eventMapper.mapToEntity(dto));
    }

    @Override
    public byte[] getEventImage(String fileName) throws IOException {
        return FileUtil.getImage(fileName);
    }

    @Override
    public void editEvent(EditEventDto dto, int id, MultipartFile[] files) throws IOException {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isEmpty()) {
            throw new IllegalStateException("Sorry, something went wrong, try again.");
        }
        Event event = eventOptional.get();
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
            event.setPictures(FileUtil.uploadImages(files));
        }
        eventRepository.save(event);
    }

    @Override
    public void deleteEvent(int id) {
        if (!eventRepository.existsById(id)) {
            throw new IllegalStateException();
        }
        eventRepository.deleteById(id);
    }

    @Override
    public EventOverview findById(int id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()) {
            throw new IllegalStateException("There is no event");
        }
        return eventMapper.mapToOverview(event.get());
    }
}

