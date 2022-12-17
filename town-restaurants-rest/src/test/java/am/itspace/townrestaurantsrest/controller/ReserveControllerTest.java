package am.itspace.townrestaurantsrest.controller;

import am.itspace.townrestaurantscommon.entity.Reserve;
import am.itspace.townrestaurantscommon.repository.ReserveRepository;
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
class ReserveControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReserveRepository reserveRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantCategoryRepository restaurantCategoryRepository;

    Reserve reserve;

    @BeforeEach
    void setUp() {
        userRepository.save(getUser());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUser().getEmail());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        restaurantCategoryRepository.save(getRestaurantCategory());
        restaurantRepository.save(getRestaurant());
        reserve = reserveRepository.save(getReserve());
    }

    @AfterEach
    public void tearDown() {
        reserveRepository.deleteAll();
    }

    @Test
    void create() throws Exception {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("reservedDate", "2022-12-09");
        objectNode.put("phoneNumber", "+37499223399");
        objectNode.put("reservedTime", "01:01:00");
        objectNode.put("restaurantId", "1");
        mvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectNode.toString()))
                .andExpect(status().isOk());
    }

    @Test
    void getAll() throws Exception {
        mvc.perform(get("/reservations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getByRole() throws Exception {
        mvc.perform(get("/reservations/byUser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void update() throws Exception {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("phoneNumber", "+37499223344");
        objectNode.put("reservedTime", "20:00:00");
        objectNode.put("reservedDate", "2022-12-10");
        mvc.perform(put("/reservations/{id}", reserve.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectNode.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phoneNumber", hasToString("+37499223344")));
    }

    @Test
    void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/reservations/{id}", reserve.getId())).
                andExpect(MockMvcResultMatchers.status().isOk());
    }
}