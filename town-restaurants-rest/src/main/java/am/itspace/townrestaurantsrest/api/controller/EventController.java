package am.itspace.townrestaurantsrest.api.controller;

import am.itspace.townrestaurantscommon.dto.event.EditEventDto;
import am.itspace.townrestaurantscommon.dto.event.EventOverview;
import am.itspace.townrestaurantscommon.dto.event.EventRequestDto;
import am.itspace.townrestaurantsrest.api.EventApi;
import am.itspace.townrestaurantsrest.serviceRest.EventService;
import am.itspace.townrestaurantsrest.utilRest.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController implements EventApi {

    private final EventService eventService;

    @Override
    @PostMapping
    public ResponseEntity<EventOverview> create(@Valid @RequestBody EventRequestDto eventRequestDto) {
        return ResponseEntity.ok(eventService.save(eventRequestDto));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<EventOverview>> getAll(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                      @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                      @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                      @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return ResponseEntity.ok(eventService.getAllEvents(pageNo, pageSize, sortBy, sortDir));
    }

    @Override
    @GetMapping("/byRestaurant/{id}")
    public ResponseEntity<List<EventOverview>> getByRestaurant(@PathVariable("id") int id,
                                                               @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                               @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                               @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                               @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return ResponseEntity.ok(eventService.getEventsByRestaurantId(id, pageNo, pageSize, sortBy, sortDir));
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
    @GetMapping("/restaurants")
    public ResponseEntity<Map<Integer, List<EventOverview>>> sortByRestaurant() {
        return ResponseEntity.ok(eventService.sortEventsByRestaurant());
    }

    @Override
    @GetMapping(value = "/getImages", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("fileName") String fileName) {
        return eventService.getEventImage(fileName);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        eventService.delete(id);
        return ResponseEntity.ok().build();
    }
}

