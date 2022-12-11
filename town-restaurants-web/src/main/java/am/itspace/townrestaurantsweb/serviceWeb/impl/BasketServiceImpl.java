package am.itspace.townrestaurantsweb.serviceWeb.impl;

import am.itspace.townrestaurantscommon.dto.basket.BasketOverview;
import am.itspace.townrestaurantscommon.dto.basket.CreateBasketDto;
import am.itspace.townrestaurantscommon.entity.Basket;
import am.itspace.townrestaurantscommon.entity.Product;
import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.mapper.BasketMapper;
import am.itspace.townrestaurantscommon.repository.BasketRepository;
import am.itspace.townrestaurantscommon.repository.ProductRepository;
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
        List<Basket> basketByUser = basketRepository.findBasketByUser(user);
        Page<Basket> basketPage = new PageImpl<>(basketByUser);
        return basketPage.map(basketMapper::mapToDto);
    }

    @Override
    public List<BasketOverview> getBaskets(User user) {
        return basketMapper.mapToDtoList(basketRepository.findBasketByUser(user));
    }

    public void addProductToBasket(int id, User user) {
        if (user == null) {
            throw new IllegalStateException();
        }
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new IllegalStateException();
        }
        Product product = productOptional.get();
        if (!basketRepository.existsByProductAndUser(product, user)) {
            CreateBasketDto basketDto = new CreateBasketDto();
            basketDto.setProductId(id);
            basketDto.setQuantity(1);
            Basket basket = basketMapper.mapToEntity(basketDto);
            basket.setUser(user);
            basketRepository.save(basket);
        } else {
            Basket basket = basketRepository.findByProductAndUser(product, user);
            basket.setQuantity(basket.getQuantity() + 1);
            basketRepository.save(basket);
        }
    }

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


    public void delete(int id, User user) {
        if (!basketRepository.existsByProductAndUser(productRepository.getReferenceById(id), user)) {
            throw new IllegalStateException();
        }
        Basket basket = basketRepository.findByProductAndUser(productRepository.getReferenceById(id), user);
        double quantity = basket.getQuantity();
        if (quantity == 1) {
            basket.setQuantity(0);
            basketRepository.delete(basket);
        } else {
            quantity = quantity - 1;
            basket.setQuantity(quantity);
            basketRepository.save(basket);
        }
    }
}
