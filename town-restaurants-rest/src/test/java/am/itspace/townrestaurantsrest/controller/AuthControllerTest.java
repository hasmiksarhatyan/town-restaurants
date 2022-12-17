package am.itspace.townrestaurantsrest.controller;

import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.repository.UserRepository;
import am.itspace.townrestaurantscommon.repository.VerificationTokenRepository;
import am.itspace.townrestaurantsrest.serviceRest.impl.VerificationTokenServiceRestImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static am.itspace.townrestaurantsrest.parameters.MockData.getUserForToken;
import static am.itspace.townrestaurantsrest.parameters.MockData.getVToken;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    VerificationTokenRepository tokenRepository;

    @Autowired
    VerificationTokenServiceRestImpl tokenService;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        tokenRepository.deleteAll();
    }

    @Test
    void authentication() throws Exception {
        userRepository.save(User.builder()
                .id(3)
                .firstName("Hayk")
                .email("hayk@gmail.com")
                .password(passwordEncoder.encode("hayk0012$"))
                .build());
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("password", "hayk0012$");
        objectNode.put("email", "hayk@gmail.com");
        mvc.perform(post("/activation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectNode.toString()))
                .andExpect(status().isOk());
    }

    @Test
    void verifyToken() throws Exception {
        tokenRepository.save(getVToken());
        userRepository.save(getUserForToken());
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("plainToken", getVToken().getPlainToken());
        mvc.perform(post("/verification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectNode.toString()))
                .andExpect(status().isOk());
    }
}
