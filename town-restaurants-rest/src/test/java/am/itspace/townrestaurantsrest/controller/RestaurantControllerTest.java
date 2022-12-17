package am.itspace.townrestaurantsrest.controller;

import am.itspace.townrestaurantscommon.dto.restaurant.CreateRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantRequestDto;
import am.itspace.townrestaurantscommon.entity.Restaurant;
import am.itspace.townrestaurantscommon.repository.ProductRepository;
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
class RestaurantControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantCategoryRepository restaurantCategoryRepository;

    Restaurant restaurant;

    @BeforeEach
    void setUp() {
        userRepository.save(getUser());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUser().getEmail());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        restaurantCategoryRepository.save(getRestaurantCategory());
        restaurant = restaurantRepository.save(getRestaurant());
    }

    @AfterEach
    public void tearDown() {
        restaurantRepository.deleteAll();
    }

    @Test
    void create() throws Exception {
        CreateRestaurantDto createRestaurantDto = getCreateRestaurant();
        RestaurantRequestDto restaurantRequestDto = getRestaurantRequestDto();
        restaurantRequestDto.setCreateRestaurantDto(createRestaurantDto);
        ObjectNode objectNode = new ObjectMapper().valueToTree(restaurantRequestDto);
        mvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectNode.toString()))
                .andExpect(status().isOk());
    }

    @Test
    void getAll() throws Exception {
        mvc.perform(get("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getAllByUser() throws Exception {
        mvc.perform(get("/restaurants/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getImage() throws Exception {
        mvc.perform(get("/restaurants/getImages")
                        .contentType(MediaType.IMAGE_JPEG_VALUE))
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
    void update() throws Exception {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("name", "Limone");
        objectNode.put("restaurantCategoryId", "1");
        objectNode.put("deliveryPrice", "200.0");
        objectNode.put("email", "limone@gmail.com");
        mvc.perform(put("/restaurants/{id}", restaurant.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectNode.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", hasToString("Limone")));
    }

    @Test
    void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/restaurants/{id}", 1)).
                andExpect(MockMvcResultMatchers.status().isOk());
    }
}