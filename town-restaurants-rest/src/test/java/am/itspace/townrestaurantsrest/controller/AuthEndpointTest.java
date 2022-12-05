package am.itspace.townrestaurantsrest.controller;

import am.itspace.townrestaurantscommon.dto.user.UserAuthDto;
import am.itspace.townrestaurantscommon.dto.user.UserAuthResponseDto;
import am.itspace.townrestaurantscommon.entity.User;
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

import static am.itspace.townrestaurantsrest.parameters.MockData.getUser;
import static am.itspace.townrestaurantsrest.parameters.MockData.getUserAuthResponseDto;
import static org.hamcrest.Matchers.hasToString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthEndpointTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    private User user;

    private UserAuthResponseDto userAuthResponseDto = getUserAuthResponseDto();

    @BeforeEach
    void setUp() {
        user = userRepository.save(getUser());
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void register() throws Exception {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("password", "hayk00");
        objectNode.put("email", "hayk@gmail.com");
        objectNode.put("firstName", "Hayk");
        mvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectNode.toString()))
                .andExpect(status().isOk());
    }

    @Test
    void auth() throws Exception {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("password", "hayk00");
        objectNode.put("email", "hayk@gmail.com");
        mvc.perform(post("/user/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectNode.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", hasToString(userAuthResponseDto.getToken())));
    }

    @Test
    void verifyToken() throws Exception {

        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("plainToken", "1234567890");
        mvc.perform(post("/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectNode.toString()))
                .andExpect(status().isOk());
    }


//    @Test
//    void successfulGettingUser() throws Exception {
//        User user = userControllerTestParameters.getSavedUser();
//        User adminUser = userControllerTestParameters.getSavedUser(ADMIN);
//
//        resultActions
//                .getById(user.getId(), adminUser)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(user.getId().toString()));
//    }

}
