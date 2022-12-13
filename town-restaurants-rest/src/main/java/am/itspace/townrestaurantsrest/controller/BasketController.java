package am.itspace.townrestaurantsrest.controller;

import am.itspace.townrestaurantscommon.dto.basket.BasketOverview;
import am.itspace.townrestaurantsrest.api.BasketApi;
import am.itspace.townrestaurantsrest.serviceRest.BasketService;
import am.itspace.townrestaurantsrest.utilRest.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/baskets")
public class BasketController implements BasketApi {

    private final BasketService basketService;

    @Override
    @GetMapping("/addTo/{id}")
    public ResponseEntity<?> create(@PathVariable("id") int productId) {
        basketService.addProductToBasket(productId);
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping
    public ResponseEntity<List<BasketOverview>> getAll() {
        return ResponseEntity.ok(basketService.getAll());
    }

    @Override
    @GetMapping("/pages")
    public ResponseEntity<List<BasketOverview>> getAll(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                       @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                       @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                       @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return ResponseEntity.ok(basketService.getAllBaskets(pageNo, pageSize, sortBy, sortDir));
    }

    @Override
    @GetMapping("/totalPrice")
    public ResponseEntity<Double> getTotalPrice() {
        return ResponseEntity.ok(basketService.getTotalPrice());
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        basketService.delete(id);
        return ResponseEntity.ok().build();
    }
}

