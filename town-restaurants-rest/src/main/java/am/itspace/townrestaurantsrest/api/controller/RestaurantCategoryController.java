package am.itspace.townrestaurantsrest.api.controller;

import am.itspace.townrestaurantscommon.dto.restaurantCategory.CreateRestaurantCategoryDto;
import am.itspace.townrestaurantscommon.dto.restaurantCategory.EditRestaurantCategoryDto;
import am.itspace.townrestaurantscommon.dto.restaurantCategory.RestaurantCategoryOverview;
import am.itspace.townrestaurantsrest.api.RestaurantCategoryApi;
import am.itspace.townrestaurantsrest.serviceRest.RestaurantCategoryService;
import am.itspace.townrestaurantsrest.utilRest.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurantsCategories")
public class RestaurantCategoryController implements RestaurantCategoryApi {

    private final RestaurantCategoryService restaurantCategoryService;

    @Override
    @PostMapping
    public ResponseEntity<RestaurantCategoryOverview> create(@Valid @RequestBody CreateRestaurantCategoryDto createRestaurantCategoryDto) {
        return ResponseEntity.ok(restaurantCategoryService.save(createRestaurantCategoryDto));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<RestaurantCategoryOverview>> getAll(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                                   @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                                   @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                                   @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return ResponseEntity.ok(restaurantCategoryService.getAllCategories(pageNo, pageSize, sortBy, sortDir));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantCategoryOverview> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok(restaurantCategoryService.getById(id));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<RestaurantCategoryOverview> update(@PathVariable("id") int id,
                                                             @Valid @RequestBody EditRestaurantCategoryDto editRestaurantCategoryDto) {
        return ResponseEntity.ok(restaurantCategoryService.update(id, editRestaurantCategoryDto));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        restaurantCategoryService.delete(id);
        return ResponseEntity.ok().build();
    }
}

