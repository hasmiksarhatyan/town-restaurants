package am.itspace.townrestaurantsrest.repository;

import am.itspace.townrestaurantscommon.entity.Basket;
import am.itspace.townrestaurantscommon.entity.Product;
import am.itspace.townrestaurantscommon.entity.Role;
import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.repository.BasketRepository;
import am.itspace.townrestaurantscommon.repository.ProductRepository;
import am.itspace.townrestaurantscommon.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BasketRepositoryTest {

    @InjectMocks
    private BasketRepository basketRepository;

    @InjectMocks
    private ProductRepository productRepository;

    @InjectMocks
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        basketRepository.deleteAll();
    }

    @Test
    void existsByProduct() {
        Product product = Product.builder()
                .name("Pizza")
                .price(2000.0)
                .build();
        productRepository.save(product);

        Basket basket = Basket.builder()
                .product(product)
                .build();
        basketRepository.save(basket);
        boolean expected = basketRepository.existsByProduct(product);
        assertTrue(expected);
    }

    @Test
    void existsByProductId() {
        Product product = Product.builder()
                .name("Pizza")
                .price(2000.0)
                .build();
        productRepository.save(product);

        Basket basket = Basket.builder()
                .product(product)
                .build();
        basketRepository.save(basket);
        boolean expected = basketRepository.existsByProductId(product.getId());
        assertTrue(expected);
    }

    @Test
    void findBasketByProductId() {
        Product product = Product.builder()
                .name("Pizza")
                .price(2000.0)
                .build();
        productRepository.save(product);
        Basket basket = Basket.builder()
                .product(product)
                .build();
        basketRepository.save(basket);
        Basket basketByProductId = basketRepository.findBasketByProductId(product.getId());
        assertNotNull(basketByProductId);
    }

    @Test
    void findAllByProductId() {
        Product product = Product.builder()
                .name("Pizza")
                .price(2000.0)
                .build();
        productRepository.save(product);
        Basket basket = Basket.builder()
                .product(product)
                .build();
        basketRepository.save(basket);
        List<Product> products = basketRepository.findAllByProductId(product.getId());
        assertNotNull(products);
    }

    @Test
    void findBasketByUser() {
        User user = User.builder()
                .email("yan@gmail.com")
                .firstName("Victoria")
                .password("password")
                .role(Role.MANAGER)
                .lastName("Yan")
                .build();
        userRepository.save(user);
        Basket basket = Basket.builder()
                .user(user)
                .build();
        basketRepository.save(basket);
        List<Basket> basketByUser = basketRepository.findBasketByUser(user);
        assertNotNull(basketByUser);
    }

    @Test
    void findByProductAndUser() {
        User user = User.builder()
                .email("yan@gmail.com")
                .firstName("Victoria")
                .password("password")
                .role(Role.MANAGER)
                .lastName("Yan")
                .build();
        userRepository.save(user);
        Product product = Product.builder()
                .name("Pizza")
                .price(2000.0)
                .user(user)
                .build();
        productRepository.save(product);
        Basket basket = Basket.builder()
                .user(user)
                .product(product)
                .build();
        basketRepository.save(basket);
        Basket basketByProductAndUser = basketRepository.findByProductAndUser(product, user);
        assertNotNull(basketByProductAndUser);
    }

    @Test
    void findByUser() {
        User user = User.builder()
                .email("yan@gmail.com")
                .firstName("Victoria")
                .password("password")
                .role(Role.MANAGER)
                .lastName("Yan")
                .build();
        userRepository.save(user);
        Basket basket = Basket.builder()
                .user(user)
                .build();
        basketRepository.save(basket);
        Optional<Basket> byUser = basketRepository.findByUser(user);
        assertNotNull(byUser);
    }
}