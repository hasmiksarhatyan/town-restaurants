package am.itspace.townrestaurantsrest.controller;

import am.itspace.townrestaurantscommon.entity.CreditCard;
import am.itspace.townrestaurantscommon.repository.CreditCardRepository;
import am.itspace.townrestaurantscommon.repository.UserRepository;
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

import static am.itspace.townrestaurantsrest.parameters.MockData.getCreditCard;
import static am.itspace.townrestaurantsrest.parameters.MockData.getUser;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreditCardControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private CreditCardRepository creditCardRepository;

    private CreditCard creditCard;

    @BeforeEach
    void setUp() {
        userRepository.save(getUser());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUser().getEmail());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        creditCard = getCreditCard();
        creditCardRepository.save(creditCard);
    }

    @AfterEach
    void tearDown() {
        creditCardRepository.deleteAll();
    }

    @Test
    void getAll() throws Exception {
        mvc.perform(get("/creditCards")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getByUser() throws Exception {
        mvc.perform(get("/creditCards/byUser", creditCard.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}