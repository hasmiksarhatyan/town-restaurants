package am.itspace.townrestaurantsrest.controller;

import am.itspace.townrestaurantscommon.entity.Basket;
import am.itspace.townrestaurantscommon.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static am.itspace.townrestaurantsrest.parameters.MockData.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BasketControllerTest {

    @Autowired
    private MockMvc mvc;

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

    @AfterEach
    void tearDown() {
        basketRepository.deleteAll();
    }

    @Test
    void create() throws Exception {
        mvc.perform(get("/baskets/addTo/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAll() throws Exception {
        mvc.perform(get("/baskets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getByUser() throws Exception {
        mvc.perform(get("/baskets/byUser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/baskets/{id}", basket.getId())).
                andExpect(MockMvcResultMatchers.status().isOk());
    }
}