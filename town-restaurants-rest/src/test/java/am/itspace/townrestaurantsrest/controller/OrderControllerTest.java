package am.itspace.townrestaurantsrest.controller;

import am.itspace.townrestaurantscommon.dto.order.CreateOrderDto;
import am.itspace.townrestaurantscommon.dto.order.OrderCreditCardDto;
import am.itspace.townrestaurantscommon.entity.Order;
import am.itspace.townrestaurantscommon.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
import static org.hamcrest.Matchers.hasToString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    BasketRepository basketRepository;

    @Autowired
    private RestaurantCategoryRepository restaurantCategoryRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    private Order order;

    @BeforeEach
    void setUp() {
        userRepository.save(getUser());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUser().getEmail());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        restaurantCategoryRepository.save(getRestaurantCategory());
        restaurantRepository.save(getRestaurant());
        productCategoryRepository.save(getProductCategory());
        productRepository.save(getProduct());
        productRepository.save(getProductForBasket());
        order = orderRepository.save(getOrder());
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
    }

    @Test
    void create() throws Exception {
        OrderCreditCardDto orderCreditCardDto = getOrderCreditCard();
        CreateOrderDto createOrderDto = getCreateOrderDto();
        orderCreditCardDto.setCreateOrderDto(createOrderDto);
//        CreateCreditCardDto creditCardDto = getCreateCreditCard();
//        orderCreditCardDto.setCreditCardDto(creditCardDto);
        ObjectNode objectNode = new ObjectMapper().valueToTree(orderCreditCardDto);
        objectNode.put("additionalAddress", "Tumanyan");
        mvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectNode.toString()))
                .andExpect(status().isOk());
    }

    @Test
    void getAll() throws Exception {
        mvc.perform(get("/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getById() throws Exception {
        mvc.perform(get("/orders/{id}", order.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.additionalAddress", hasToString(order.getAdditionalAddress())));
    }

    @Test
    void update() throws Exception {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("additionalAddress", "Teryan");
        mvc.perform(put("/orders/{id}", order.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectNode.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.additionalAddress", hasToString("Teryan")));
    }

    @Test
    void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/orders/{id}", order.getId())).
                andExpect(MockMvcResultMatchers.status().isOk());
    }
}