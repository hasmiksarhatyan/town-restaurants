package am.itspace.townrestaurantsrest.controller;


import am.itspace.townrestaurantscommon.dto.event.EventOverview;
import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantscommon.dto.restaurant.CreateRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.EditRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantsrest.api.RestaurantApi;
import am.itspace.townrestaurantsrest.serviceRest.EventService;
import am.itspace.townrestaurantsrest.serviceRest.ProductService;
import am.itspace.townrestaurantsrest.serviceRest.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantEndpoint implements RestaurantApi {

    private final EventService eventService;
    private final ProductService productService;
    private final RestaurantService restaurantService;

    @Override
    @PostMapping
    public ResponseEntity<RestaurantOverview> create(@Valid @RequestBody CreateRestaurantDto createRestaurantDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.save(createRestaurantDto));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<RestaurantOverview>> getAll() {
        return ResponseEntity.ok(restaurantService.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantOverview> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok(restaurantService.getById(id));
    }

    @Override
    @GetMapping("/events/{id}")
    public ResponseEntity<List<EventOverview>> findEventsByRestaurantId(@PathVariable("id") int id) {
        return ResponseEntity.ok(eventService.findEventsByRestaurantId(id));
    }

    @Override
    @GetMapping("/products/{id}")
    public ResponseEntity<List<ProductOverview>> findProductsByRestaurantId(@PathVariable("id") int id) {
        return ResponseEntity.ok(productService.findProductsByRestaurant(id));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<RestaurantOverview> update(@Valid @PathVariable("id") int id,
                                                     @RequestBody EditRestaurantDto editRestaurantDto) {
        return ResponseEntity.ok(restaurantService.update(id, editRestaurantDto));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        restaurantService.delete(id);
        return ResponseEntity.ok().build();
    }
}

