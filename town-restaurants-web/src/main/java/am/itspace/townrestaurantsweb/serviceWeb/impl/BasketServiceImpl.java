package am.itspace.townrestaurantsweb.serviceWeb.impl;

import am.itspace.townrestaurantscommon.dto.basket.BasketOverview;
import am.itspace.townrestaurantscommon.entity.Basket;
import am.itspace.townrestaurantscommon.entity.Product;
import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.mapper.BasketMapper;
import am.itspace.townrestaurantscommon.repository.BasketRepository;
import am.itspace.townrestaurantscommon.repository.ProductRepository;
import am.itspace.townrestaurantscommon.security.CurrentUser;
import am.itspace.townrestaurantsweb.serviceWeb.BasketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final BasketMapper basketMapper;
    private final ProductRepository productRepository;

    @Override
    public Page<BasketOverview> getBaskets(Pageable pageable, User user) {
        Page<Basket> basketByUser = basketRepository.findBasketByUser(user, pageable);
        if (basketByUser.isEmpty()) {
            throw new IllegalStateException("You don't have a basket");
        }
        List<BasketOverview> basketOverviews = basketMapper.mapToDto(basketByUser);
        return new PageImpl<>(basketOverviews, pageable, basketOverviews.size());
    }

    @Override
    public void addBasket(int id, CurrentUser currentUser) {
        if (currentUser == null) {
            throw new IllegalStateException();
        }
        Basket basket = new Basket();
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new IllegalStateException();
        }
        Product product = productOptional.get();
        if (!basketRepository.existsByProduct(product)) {
            basket.setProduct(product);
            basket.setQuantity(1);
            basket.setUser(currentUser.getUser());
            basketRepository.save(basket);
        } else {
            basket = basketRepository.findByProductAndUser(product, currentUser.getUser());
            basket.setQuantity(basket.getQuantity() + 1);
            basketRepository.save(basket);
        }
    }

    @Override
    public void delete(int id) {
        if (!basketRepository.existsByProductId(id)) {
            throw new IllegalStateException();
        }
        Basket basket = basketRepository.findBasketByProductId(id);
        double quantity = basket.getQuantity();
        quantity = quantity-1;
        basket.setQuantity(quantity);
        basketRepository.save(basket);
    }
}
