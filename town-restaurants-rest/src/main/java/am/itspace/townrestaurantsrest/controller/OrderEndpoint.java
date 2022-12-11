package am.itspace.townrestaurantsrest.controller;

import am.itspace.townrestaurantscommon.dto.FetchRequestDto;
import am.itspace.townrestaurantscommon.dto.creditCard.CreateCreditCardDto;
import am.itspace.townrestaurantscommon.dto.order.CreateOrderDto;
import am.itspace.townrestaurantscommon.dto.order.EditOrderDto;
import am.itspace.townrestaurantscommon.dto.order.OrderOverview;
import am.itspace.townrestaurantscommon.entity.Order;
import am.itspace.townrestaurantsrest.serviceRest.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderEndpoint {

    private final ModelMapper modelMapper;
    private final OrderService orderService;


    @PostMapping
    public ResponseEntity<OrderOverview> create(@Valid @RequestBody CreateOrderDto createOrderDto, CreateCreditCardDto creditCardDto) {
        return ResponseEntity.ok(orderService.save(createOrderDto,creditCardDto));
    }

    @GetMapping
    public ResponseEntity<List<OrderOverview>> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }


    @GetMapping("/pages")
    public ResponseEntity<List<OrderOverview>> getAll(@Valid @RequestBody FetchRequestDto fetchRequestDto) {
        List<Order> orders = orderService.getOrdersList(fetchRequestDto);
        return ResponseEntity.ok(orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderOverview> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    private OrderOverview convertToDto(Order order) {
        OrderOverview orderOverview = modelMapper.map(order, OrderOverview.class);
        orderOverview.setAdditionalAddress(order.getAdditionalAddress());
        return orderOverview;
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderOverview> update(@PathVariable("id") int id,
                                                @Valid @RequestBody EditOrderDto editOrderDto) {
        return ResponseEntity.ok(orderService.update(id, editOrderDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        orderService.delete(id);
        return ResponseEntity.ok().build();
    }

}

