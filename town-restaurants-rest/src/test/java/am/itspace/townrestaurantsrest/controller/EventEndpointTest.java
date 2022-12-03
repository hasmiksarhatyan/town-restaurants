package am.itspace.townrestaurantsrest.controller;

import am.itspace.townrestaurantscommon.entity.Event;
import am.itspace.townrestaurantscommon.repository.EventRepository;
import am.itspace.townrestaurantscommon.repository.RestaurantCategoryRepository;
import am.itspace.townrestaurantscommon.repository.RestaurantRepository;
import am.itspace.townrestaurantscommon.repository.UserRepository;
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
class EventEndpointTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantCategoryRepository restaurantCategoryRepository;

    Event event;

    @BeforeEach
    void setUp() {
        userRepository.save(getUser());
        restaurantCategoryRepository.save(getRestaurantCategory());
        restaurantRepository.save(getRestaurant());
        event = eventRepository.save(getEvent());
    }

    @AfterEach
    public void tearDown() {
        eventRepository.deleteAll();
    }

    @Test
    void create() throws Exception {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("name", "Ital");
        objectNode.put("restaurantId", "1");
        mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectNode.toString()))
                .andExpect(status().isOk());
    }

    @Test
    void getAll() throws Exception {
        mvc.perform(get("/events")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getById() throws Exception {
        mvc.perform(get("/events/{id}", event.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", hasToString(event.getName())));
    }

    @Test
    void update() throws Exception {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("name", "Limone");
        mvc.perform(put("/events/{id}", event.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectNode.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", hasToString("Limone")));
    }

    @Test
    void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/events/{id}", event.getId())).
                andExpect(MockMvcResultMatchers.status().isOk());
    }
}