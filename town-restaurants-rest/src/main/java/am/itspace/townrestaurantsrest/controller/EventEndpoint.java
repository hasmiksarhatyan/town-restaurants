package am.itspace.townrestaurantsrest.controller;

import am.itspace.townrestaurantscommon.dto.FileDto;
import am.itspace.townrestaurantscommon.dto.event.CreateEventDto;
import am.itspace.townrestaurantscommon.dto.event.EditEventDto;
import am.itspace.townrestaurantscommon.dto.event.EventOverview;
import am.itspace.townrestaurantscommon.dto.FetchRequestDto;
import am.itspace.townrestaurantscommon.entity.Event;
import am.itspace.townrestaurantsrest.api.EventApi;
import am.itspace.townrestaurantsrest.serviceRest.EventService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventEndpoint implements EventApi {

    private final ModelMapper modelMapper;
    private final EventService eventService;

//    @Override
//    @PostMapping
//    public ResponseEntity<EventOverview> create(@Valid @RequestBody CreateEventDto createEventDto,
//                                                @RequestParam("pictures") MultipartFile[] files) {
//        return ResponseEntity.ok(eventService.save(createEventDto,files));
//    }

    @PostMapping
    public ResponseEntity<EventOverview> create(@Valid @ModelAttribute CreateEventDto createEventDto,
                                                @ModelAttribute FileDto fileDto) {
        return ResponseEntity.ok(eventService.save(createEventDto, fileDto));
    }

    @Override
    @GetMapping(value = "/getImages", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("fileName") String fileName) {
        return eventService.getEventImage(fileName);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<EventOverview>> getAll() {
        return ResponseEntity.ok(eventService.getAll());
    }

    @Override
    @GetMapping("/restaurants")
    public ResponseEntity<Map<Integer, List<EventOverview>>> sortAllByRestaurant() {
        return ResponseEntity.ok(eventService.sortEventsByRestaurant());
    }

    @Override
    @GetMapping("/pages")
    public ResponseEntity<List<EventOverview>> getAll(@Valid @RequestBody FetchRequestDto fetchRequestDto) {
        List<Event> events = eventService.getEventsList(fetchRequestDto);
        return ResponseEntity.ok(events.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<EventOverview> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok(eventService.getById(id));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<EventOverview> update(@PathVariable("id") int id,
                                                @Valid @RequestBody EditEventDto editEventDto) {
        return ResponseEntity.ok(eventService.update(id, editEventDto));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        eventService.delete(id);
        return ResponseEntity.ok().build();
    }

    private EventOverview convertToDto(Event event) {
        EventOverview eventOverview = modelMapper.map(event, EventOverview.class);
        eventOverview.setName(eventOverview.getName());
        return eventOverview;
    }
}

