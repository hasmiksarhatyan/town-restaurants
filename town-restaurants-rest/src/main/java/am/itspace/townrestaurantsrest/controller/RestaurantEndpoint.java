package am.itspace.townrestaurantsrest.controller;


import am.itspace.townrestaurantscommon.dto.restaurant.CreateRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.EditRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantsrest.api.RestaurantApi;
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

