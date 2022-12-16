package am.itspace.townrestaurantsrest.controller;

import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.repository.UserRepository;
import am.itspace.townrestaurantscommon.repository.VerificationTokenRepository;
import am.itspace.townrestaurantscommon.service.impl.MailServiceImpl;
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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.mail.internet.MimeMessage;

import static am.itspace.townrestaurantsrest.parameters.MockData.*;
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
    MailServiceImpl mailService;

    @Autowired
    JavaMailSender javaMailSender;

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
    //3,,2+
    @Test
    void register() throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        tokenService.createToken(getUser());
        mailService.sendEmail("a", "a", "1");
        javaMailSender.send(mimeMessage);
        objectNode.put("password", "hayk0000$");
        objectNode.put("email", "hayk@mail.com");
        objectNode.put("firstName", "Hayk");
        mvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectNode.toString()))
                .andExpect(status().isOk());
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
