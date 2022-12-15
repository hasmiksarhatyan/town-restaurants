package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.basket.BasketOverview;
import am.itspace.townrestaurantscommon.entity.Basket;
import am.itspace.townrestaurantscommon.mapper.BasketMapper;
import am.itspace.townrestaurantscommon.repository.BasketRepository;
import am.itspace.townrestaurantscommon.repository.ProductRepository;
import am.itspace.townrestaurantscommon.security.CurrentUser;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static am.itspace.townrestaurantsrest.parameters.MockData.*;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BasketServiceImplTest {

    @InjectMocks
    BasketServiceImpl basketService;

    @Mock
    BasketMapper basketMapper;

    @Mock
    BasketRepository basketRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    SecurityContextServiceImpl securityContextService;

    @Test
    void shouldAddProductToBasket() {
        //given
        var productId = 1;
        var basket = getBasket2();
        var product = getProduct();
        CurrentUser currentUser = new CurrentUser(getUser());
        //when
        doReturn(currentUser).when(securityContextService).getUserDetails();
        doReturn(Optional.of(product)).when(productRepository).findById(productId);
        doReturn(false).when(basketRepository).existsByProductAndUser(product, currentUser.getUser());
        basket.setProduct(product);
        basket.setQuantity(1);
        basket.setUser(currentUser.getUser());
        doReturn(basket).when(basketRepository).save(any(Basket.class));
        basketService.addProductToBasket(productId);
        //then
        verify(basketRepository, times(1)).save(basket);
    }

    @Test
    void shouldAddProductToBasketWhenProductExists() {
        //given
        var productId = 1;
        var basket = getBasket();
        var product = getProduct();
        CurrentUser currentUser = new CurrentUser(getUser());
        //when
        doReturn(currentUser).when(securityContextService).getUserDetails();
        doReturn(Optional.of(product)).when(productRepository).findById(productId);
        doReturn(true).when(basketRepository).existsByProductAndUser(product, currentUser.getUser());
        doReturn(basket).when(basketRepository).findByProductAndUser(product, currentUser.getUser());
        basket.setQuantity(basket.getQuantity() + 1);
        basketService.addProductToBasket(productId);
        //then
        verify(basketRepository, times(1)).save(basket);
    }

    @Test
    void saveThrowsEntityNotFoundException() {
        //given
        var productId = 1;
        CurrentUser currentUser = new CurrentUser(getUser());
        //when
        doReturn(currentUser).when(securityContextService).getUserDetails();
        doThrow(EntityNotFoundException.class).when(productRepository).findById(productId);
        //then
        assertThrows(EntityNotFoundException.class, () -> basketService.addProductToBasket(productId));
    }

    @Test
    void shouldGetAllBaskets() {
        //given
        var listOfPageableBaskets = getPageBaskets();
        var expected = List.of(getBasketOverview(), getBasketOverview());
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(listOfPageableBaskets).when(basketRepository).findAll(pageable);
        doReturn(expected).when(basketMapper).mapToDtoList(anyList());
        List<BasketOverview> actual = basketService.getAll(1, 1, "name", "DESC");
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getAllBasketsShouldThrowException() {
        //given
        Page<Basket> empty = Page.empty();
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(empty).when(basketRepository).findAll(pageable);
        //then
        assertThrows(EntityNotFoundException.class, () -> basketService.getAll(1, 1, "name", "DESC"));
    }

    @Test
    void shouldGetByUser() {
        //given
        CurrentUser currentUser = new CurrentUser(getManagerUser());
        var listOfPageableBaskets = getPageBaskets();
        var expected = List.of(getBasketOverview(), getBasketOverview());
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(currentUser).when(securityContextService).getUserDetails();
        doReturn(listOfPageableBaskets).when(basketRepository).findAllByUser(currentUser.getUser(), pageable);
        doReturn(expected).when(basketMapper).mapToDtoList(anyList());
        List<BasketOverview> actual = basketService.getAllByUser(1, 1, "name", "DESC");
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getByUserShouldThrowException() {
        //given
        Page<Basket> empty = Page.empty();
        CurrentUser currentUser = new CurrentUser(getManagerUser());
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(currentUser).when(securityContextService).getUserDetails();
        doReturn(empty).when(basketRepository).findAllByUser(currentUser.getUser(), pageable);
        //then
        assertThrows(EntityNotFoundException.class, () -> basketService.getAllByUser(1, 1, "name", "DESC"));
    }

    @Test
    void successTotalPrice() {
        //given
        var product = getProduct();
        var baskets = List.of(getBasket());
        var basket = getBasket();
        var expected = 0;
        CurrentUser currentUser = new CurrentUser(getUser());
        //when
        doReturn(currentUser).when(securityContextService).getUserDetails();
        doReturn(baskets).when(basketRepository).findBasketByUser(currentUser.getUser());
        expected += product.getPrice() * basket.getQuantity();
        double actual = basketService.getTotalPrice();
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void deleteSuccessIfQuantityIsNotOne() {
        //given
        var productId = 1;
        var basket = getBasket();
        CurrentUser currentUser = new CurrentUser(getUser());
        //when
        doReturn(currentUser).when(securityContextService).getUserDetails();
        doReturn(true).when(basketRepository).existsByProductAndUser(null, currentUser.getUser());
        doReturn(basket).when(basketRepository).findByProductAndUser(null, currentUser.getUser());
        double quantity = basket.getQuantity();
        basket.setQuantity(quantity - 1);
        doReturn(basket).when(basketRepository).save(any(Basket.class));
        basketService.delete(productId);
        //then
        verify(basketRepository, times(1)).save(basket);
    }

    @Test
    void deleteSuccessIfQuantityIsOne() {
        //given
        var productId = 1;
        var basket = getBasket();
        CurrentUser currentUser = new CurrentUser(getUser());
        //when
        doReturn(currentUser).when(securityContextService).getUserDetails();
        doReturn(true).when(basketRepository).existsByProductAndUser(null, currentUser.getUser());
        doReturn(basket).when(basketRepository).findByProductAndUser(null, currentUser.getUser());
        basket.setQuantity(0);
        basketRepository.delete(basket);
        basketService.delete(productId);
        //then
        verify(basketRepository, times(1)).delete(basket);
    }

    @Test
    void deleteThrowException() {
        //given
        var productId = 1;
        CurrentUser currentUser = new CurrentUser(getUser());
        //when
        doReturn(currentUser).when(securityContextService).getUserDetails();
        doReturn(false).when(basketRepository).existsByProductAndUser(null, currentUser.getUser());
        //then
        assertThrows(EntityNotFoundException.class, () -> basketService.delete(productId));
    }
}

