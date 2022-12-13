package am.itspace.townrestaurantsrest.controller;

import am.itspace.townrestaurantscommon.dto.FileDto;
import am.itspace.townrestaurantscommon.dto.event.EventOverview;
import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantscommon.dto.restaurant.CreateRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.EditRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantsrest.api.RestaurantApi;
import am.itspace.townrestaurantsrest.serviceRest.EventService;
import am.itspace.townrestaurantsrest.serviceRest.ProductService;
import am.itspace.townrestaurantsrest.serviceRest.RestaurantService;
import am.itspace.townrestaurantsrest.utilRest.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController implements RestaurantApi {

    private final EventService eventService;
    private final ProductService productService;
    private final RestaurantService restaurantService;

    @Override
    @PostMapping
    public ResponseEntity<RestaurantOverview> create(@Valid @ModelAttribute CreateRestaurantDto createRestaurantDto,
                                                     @ModelAttribute FileDto fileDto) {
        return ResponseEntity.ok(restaurantService.save(createRestaurantDto, fileDto));
    }

    @GetMapping(value = "/getImages", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("fileName") String fileName) {
        return restaurantService.getRestaurantImage(fileName);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<RestaurantOverview>> getAll() {
        return ResponseEntity.ok(restaurantService.getAll());
    }

    //localhost:8080/api/v1/restaurants/pages/?pageSize=2&sortBy=name&sortdir=DESC
    @Override
    @GetMapping("/pages")
    public ResponseEntity<List<RestaurantOverview>> getAll(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                           @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                           @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                           @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return ResponseEntity.ok(restaurantService.getAllRestaurants(pageNo, pageSize, sortBy, sortDir));
    }

    @GetMapping("/user")
    public ResponseEntity<List<RestaurantOverview>> getAllByUser(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                                 @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                                 @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                                 @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return ResponseEntity.ok(restaurantService.getRestaurantsByUser(pageNo, pageSize, sortBy, sortDir));
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
}

