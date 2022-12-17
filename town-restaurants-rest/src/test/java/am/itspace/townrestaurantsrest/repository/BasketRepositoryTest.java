package am.itspace.townrestaurantsrest.repository;

import am.itspace.townrestaurantscommon.entity.Basket;
import am.itspace.townrestaurantscommon.entity.Product;
import am.itspace.townrestaurantscommon.entity.Role;
import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static am.itspace.townrestaurantsrest.parameters.MockData.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BasketRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    RestaurantCategoryRepository restaurantCategoryRepository;

    private Basket basket;

    @AfterEach
    void tearDown() {
        basketRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        userRepository.save(getUser());
        restaurantCategoryRepository.save(getRestaurantCategory());
        restaurantRepository.save(getRestaurant());
        productCategoryRepository.save(getProductCategory());
        productRepository.save(getProduct());
        productRepository.save(getProductForBasket());
        orderRepository.save(getOrder());
        basket = getBasket();
        basketRepository.save(basket);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUser().getEmail());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    @Test
    void existsByProduct() {
        boolean expected = basketRepository.existsByProduct(getProduct());
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

//    @Test
//    void findAllByProductId() {
//        Product product = Product.builder()
//                .name("Pizza")
//                .price(2000.0)
//                .build();
//        productRepository.save(product);
//        Basket basket = Basket.builder()
//                .product(product)
//                .build();
//        basketRepository.save(basket);
//        List<Product> products = basketRepository.findAllByProductId(product.getId());
//        assertNotNull(products);
//    }

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