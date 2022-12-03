package am.itspace.townrestaurantsrest.controller;

import am.itspace.townrestaurantscommon.dto.event.CreateEventDto;
import am.itspace.townrestaurantscommon.dto.event.EditEventDto;
import am.itspace.townrestaurantscommon.dto.event.EventOverview;
import am.itspace.townrestaurantsrest.api.EventApi;
import am.itspace.townrestaurantsrest.serviceRest.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventEndpoint implements EventApi {

    private final EventService eventService;

    @Override
    @PostMapping
    public ResponseEntity<EventOverview> create(@Valid @RequestBody CreateEventDto createEventDto) {
        return ResponseEntity.ok(eventService.save(createEventDto));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<EventOverview>> getAll() {
        return ResponseEntity.ok(eventService.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<EventOverview> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok(eventService.getById(id));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<EventOverview> update(@Valid @PathVariable("id") int id,
                                                @RequestBody EditEventDto editEventDto) {
        return ResponseEntity.ok(eventService.update(id, editEventDto));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        eventService.delete(id);
        return ResponseEntity.ok().build();
    }
}

