package am.itspace.townrestaurantsrest.api.controller;

import am.itspace.townrestaurantscommon.dto.restaurant.EditRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantRequestDto;
import am.itspace.townrestaurantsrest.api.RestaurantApi;
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

    private final RestaurantService restaurantService;

    @Override
    @PostMapping
    public ResponseEntity<RestaurantOverview> create(@Valid @RequestBody RestaurantRequestDto restaurantRequestDto) {
        return ResponseEntity.ok(restaurantService.save(restaurantRequestDto));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<RestaurantOverview>> getAll(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                           @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                           @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                           @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return ResponseEntity.ok(restaurantService.getAll(pageNo, pageSize, sortBy, sortDir));
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

    @GetMapping(value = "/getImages", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("fileName") String fileName) {
        return restaurantService.getRestaurantImage(fileName);
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

