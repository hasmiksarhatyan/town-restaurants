package am.itspace.townrestaurantsrest.api.controller;

import am.itspace.townrestaurantscommon.dto.order.EditOrderDto;
import am.itspace.townrestaurantscommon.dto.order.OrderCreditCardDto;
import am.itspace.townrestaurantscommon.dto.order.OrderOverview;
import am.itspace.townrestaurantsrest.api.OrderApi;
import am.itspace.townrestaurantsrest.serviceRest.OrderService;
import am.itspace.townrestaurantsrest.utilRest.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController implements OrderApi {

    private final OrderService orderService;

    @Override
    @PostMapping
    public ResponseEntity<OrderOverview> create(@Valid @RequestBody OrderCreditCardDto orderCreditCardDto) {
        return ResponseEntity.ok(orderService.save(orderCreditCardDto));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<OrderOverview>> getAll(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                      @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                      @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                      @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return ResponseEntity.ok(orderService.getAllOrders(pageNo, pageSize, sortBy, sortDir));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<OrderOverview> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<OrderOverview> update(@PathVariable("id") int id,
                                                @Valid @RequestBody EditOrderDto editOrderDto) {
        return ResponseEntity.ok(orderService.update(id, editOrderDto));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        orderService.delete(id);
        return ResponseEntity.ok().build();
    }
}

