package am.itspace.townrestaurantsrest.controller;

import am.itspace.townrestaurantscommon.entity.Event;
import am.itspace.townrestaurantscommon.entity.Product;
import am.itspace.townrestaurantscommon.entity.Restaurant;
import am.itspace.townrestaurantscommon.entity.RestaurantCategory;
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


@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestaurantEndpointTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantCategoryRepository restaurantCategoryRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    ProductRepository productRepository;

    Restaurant restaurant;

    RestaurantCategory restaurantCategory;

    Event event;

    Product product;

    @BeforeEach
    void setUp() {
        userRepository.save(getUser());
        restaurantCategory = restaurantCategoryRepository.save(getRestaurantCategory());
        restaurant = restaurantRepository.save(getRestaurant());
        event = eventRepository.save(getEvent());
        product = productRepository.save(product);
    }

    @AfterEach
    public void tearDown() {
        restaurantRepository.deleteAll();
    }

    @Test
    void create() throws Exception {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("name", "Aperitivo");
        objectNode.put("restaurantCategoryId", getRestaurantCategory().getId());
        mvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectNode.toString()))
                .andExpect(status().isCreated());
    }

    @Test
    void getAll() throws Exception {
        mvc.perform(get("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getById() throws Exception {
        mvc.perform(get("/restaurants/{id}", restaurant.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", hasToString(restaurant.getName())));
    }

    @Test
    void findEventsByRestaurantId() throws Exception {
        mvc.perform(get("/restaurants/events/{id}", restaurant.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void findProductsByRestaurantId() throws Exception {
        mvc.perform(get("/restaurants/products/{id}", restaurant.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void update() throws Exception {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("name", "Limone");
        mvc.perform(put("/restaurants/{id}", restaurant.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectNode.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", hasToString("Limone")));
    }

    @Test
    void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/restaurants/{id}", restaurant.getId())).
                andExpect(MockMvcResultMatchers.status().isOk());
    }
}