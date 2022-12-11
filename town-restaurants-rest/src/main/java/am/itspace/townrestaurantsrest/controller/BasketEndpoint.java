package am.itspace.townrestaurantsrest.controller;

import am.itspace.townrestaurantscommon.dto.FetchRequestDto;
import am.itspace.townrestaurantscommon.dto.basket.BasketOverview;
import am.itspace.townrestaurantscommon.entity.Basket;
import am.itspace.townrestaurantscommon.mapper.BasketMapper;
import am.itspace.townrestaurantscommon.mapper.UserMapper;
import am.itspace.townrestaurantsrest.api.BasketApi;
import am.itspace.townrestaurantsrest.serviceRest.BasketService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/baskets")
public class BasketEndpoint implements BasketApi {

    private final UserMapper userMapper;
    private final ModelMapper modelMapper;
    private final BasketMapper basketMapper;
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
    public ResponseEntity<List<BasketOverview>> getAll(@Valid @RequestBody FetchRequestDto fetchRequestDto) {
        List<Basket> baskets = basketService.getBasketsList(fetchRequestDto);
        return ResponseEntity.ok(baskets.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
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

    private BasketOverview convertToDto(Basket basket) {
        BasketOverview basketOverview = modelMapper.map(basket, BasketOverview.class);
        basketOverview.setUserOverview(userMapper.mapToResponseDto(basket.getUser()));
        return basketOverview;
    }
}

