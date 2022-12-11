package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.entity.Basket;
import am.itspace.townrestaurantscommon.entity.Product;
import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.mapper.BasketMapper;
import am.itspace.townrestaurantscommon.repository.BasketRepository;
import am.itspace.townrestaurantscommon.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BasketServiceImpl {

    private final BasketMapper basketMapper;
    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;


    public double totalPrice(User user) {
        double totalPrice = 0;
        List<Basket> basketByUser = basketRepository.findBasketByUser(user);
        if (!basketByUser.isEmpty()) {
            for (Basket basket : basketByUser) {
                Product product = basket.getProduct();
                totalPrice += product.getPrice() * basket.getQuantity();
            }
        }
        return totalPrice;
    }


}
