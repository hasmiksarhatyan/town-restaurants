package am.itspace.townrestaurantsrest.controller;


import am.itspace.townrestaurantscommon.dto.event.EventOverview;
import am.itspace.townrestaurantscommon.dto.fetchRequest.FetchRequestDto;
import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantscommon.dto.restaurant.CreateRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.EditRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantscommon.entity.Restaurant;
import am.itspace.townrestaurantsrest.api.RestaurantApi;
import am.itspace.townrestaurantsrest.serviceRest.EventService;
import am.itspace.townrestaurantsrest.serviceRest.ProductService;
import am.itspace.townrestaurantsrest.serviceRest.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantEndpoint implements RestaurantApi {

    private final ModelMapper modelMapper;
    private final EventService eventService;
    private final ProductService productService;
    private final RestaurantService restaurantService;

    @Override
    @PostMapping
    public ResponseEntity<RestaurantOverview> create(@Valid @RequestBody CreateRestaurantDto createRestaurantDto) {
        return ResponseEntity.ok(restaurantService.save(createRestaurantDto));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<RestaurantOverview>> getAll() {
        return ResponseEntity.ok(restaurantService.getAll());
    }

    @Override
    @GetMapping("/pages")
    public ResponseEntity<List<RestaurantOverview>> getAll(@Valid @RequestBody FetchRequestDto fetchRequestDto) {
        List<Restaurant> restaurants = restaurantService.getRestaurantsList(fetchRequestDto);
        return ResponseEntity.ok(restaurants.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
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
    public ResponseEntity<RestaurantOverview> update(@PathVariable("id") int id,
                                                     @Valid @RequestBody EditRestaurantDto editRestaurantDto) {
        return ResponseEntity.ok(restaurantService.update(id, editRestaurantDto));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        restaurantService.delete(id);
        return ResponseEntity.ok().build();
    }

    private RestaurantOverview convertToDto(Restaurant restaurant) {
        RestaurantOverview restaurantOverview = modelMapper.map(restaurant, RestaurantOverview.class);
        restaurantOverview.setEmail(restaurant.getEmail());
        return restaurantOverview;
    }

//
//    @PostMapping(
//            value = "/{id}/image",
//            consumes = {"multipart/form-data"})
//    @PreAuthorize("#user.id == #id")
//    public ResponseEntity<ImageOverview> uploadImage(
//            @PathVariable UUID id,
//            @RequestParam("file") MultipartFile multipartFile,
//            @AuthenticationPrincipal AppUserDetails user) {
//        log.info("Attempt to upload new photo by user with id: {}", id);
//
//        return ResponseEntity.ok(restaurantService.uploadImage(id, multipartFile));
//    }

}

