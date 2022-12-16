package am.itspace.townrestaurantsrest.controller;

import am.itspace.townrestaurantscommon.entity.Payment;
import am.itspace.townrestaurantscommon.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
class PaymentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    RestaurantCategoryRepository restaurantCategoryRepository;

    private Payment payment;

    @BeforeEach
    void setUp() {
        userRepository.save(getUser());
        restaurantCategoryRepository.save(getRestaurantCategory());
        restaurantRepository.save(getRestaurant());
        productCategoryRepository.save(getProductCategory());
        productRepository.save(getProduct());
        productRepository.save(getProductForBasket());
        orderRepository.save(getOrder());
        payment = getPayment();
        paymentRepository.save(payment);
    }

    @AfterEach
    void tearDown() {
        paymentRepository.deleteAll();
    }

    @Test
    void getAll() throws Exception {
        mvc.perform(get("/payments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/payments/{id}", payment.getId())).
                andExpect(MockMvcResultMatchers.status().isOk());
    }
}