package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.basket.BasketOverview;
import am.itspace.townrestaurantscommon.entity.Basket;
import am.itspace.townrestaurantscommon.entity.Product;
import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.mapper.BasketMapper;
import am.itspace.townrestaurantscommon.repository.BasketRepository;
import am.itspace.townrestaurantscommon.repository.ProductRepository;
import am.itspace.townrestaurantsrest.exception.AuthenticationException;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;
import am.itspace.townrestaurantsrest.exception.Error;
import am.itspace.townrestaurantsrest.serviceRest.BasketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static am.itspace.townrestaurantsrest.exception.Error.BASKET_NOT_FOUND;
import static am.itspace.townrestaurantsrest.exception.Error.NEEDS_AUTHENTICATION;

@Slf4j
@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final BasketMapper basketMapper;
    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;
    private final SecurityContextServiceImpl securityContextService;

    @Override
    public void addProductToBasket(int productId) {
        try {
            User user = securityContextService.getUserDetails().getUser();
            Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(Error.PRODUCT_NOT_FOUND));
            if (!basketRepository.existsByProductAndUser(product, user)) {
                Basket basket = new Basket();
                basket.setQuantity(1);
                basket.setProduct(product);
                basket.setUser(user);
                basketRepository.save(basket);
                log.info("Product successfully added to basket");
            } else {
                Basket basket = basketRepository.findByProductAndUser(product, user);
                basket.setQuantity(basket.getQuantity() + 1);
                basketRepository.save(basket);
                log.info("Product successfully added to basket");
            }
        } catch (ClassCastException e) {
            throw new AuthenticationException(NEEDS_AUTHENTICATION);
        }
    }

    @Override
    public double getTotalPrice() {
        try {
            User user = securityContextService.getUserDetails().getUser();
            double totalPrice = 0;
            List<Basket> basketByUser = basketRepository.findBasketByUser(user);
            if (!basketByUser.isEmpty()) {
                for (Basket basket : basketByUser) {
                    Product product = basket.getProduct();
                    totalPrice += product.getPrice() * basket.getQuantity();
                }
            } else {
                throw new EntityNotFoundException(BASKET_NOT_FOUND);
            }
            log.info("The total price is calculated");
            return totalPrice;
        } catch (ClassCastException e) {
            throw new AuthenticationException(NEEDS_AUTHENTICATION);
        }
    }

    @Override
    public List<BasketOverview> getAllBaskets(int pageNo, int pageSize, String sortBy, String sortDir) {
        try {
            User user = securityContextService.getUserDetails().getUser();
            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();
            Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
            Page<Basket> baskets = basketRepository.findAllByUser(user, pageable);
            if (baskets.isEmpty()) {
                log.info("Basket not found");
                throw new EntityNotFoundException(Error.BASKET_NOT_FOUND);
            }
            List<Basket> listOfBaskets = baskets.getContent();
            log.info("Baskets successfully found");
            return new ArrayList<>(basketMapper.mapToDtoList(listOfBaskets));
        } catch (ClassCastException e) {
            throw new AuthenticationException(NEEDS_AUTHENTICATION);
        }
    }

    @Override
    public List<BasketOverview> getAll() {
        List<Basket> baskets = basketRepository.findAll();
        if (baskets.isEmpty()) {
            log.info("Basket not found");
            throw new EntityNotFoundException(Error.BASKET_NOT_FOUND);
        }
        log.info("Basket successfully found");
        return basketMapper.mapToDtoList(baskets);
    }

    @Override
    public void delete(int productId) {
        try {
            User user = securityContextService.getUserDetails().getUser();
            if (!basketRepository.existsByProductAndUser(productRepository.getReferenceById(productId), user)) {
                throw new EntityNotFoundException(BASKET_NOT_FOUND);
            }
            Basket basket = basketRepository.findByProductAndUser(productRepository.getReferenceById(productId), user);
            double quantity = basket.getQuantity();
            if (quantity == 1) {
                basket.setQuantity(0);
                basketRepository.delete(basket);
                log.info("The product has been successfully deleted from basket");
            } else {
                quantity = quantity - 1;
                basket.setQuantity(quantity);
                basketRepository.save(basket);
                log.info("The product has been successfully deleted from basket");
            }
        } catch (ClassCastException e) {
            throw new AuthenticationException(NEEDS_AUTHENTICATION);
        }
    }
}
