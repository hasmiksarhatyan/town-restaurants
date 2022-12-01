package am.itspace.townrestaurantsrest.repository;

import am.itspace.townrestaurantscommon.entity.CreditCard;
import am.itspace.townrestaurantscommon.entity.Role;
import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.repository.CreditCardRepository;
import am.itspace.townrestaurantscommon.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CreditCardRepositoryTest {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void findCreditCardByUser() {
        User user = User.builder()
                .email("yan@gmail.com")
                .firstName("Victoria")
                .password(passwordEncoder.encode("password"))
                .role(Role.MANAGER)
                .lastName("Yan")
                .build();
        userRepository.save(user);
        CreditCard creditCard = CreditCard.builder()
                .user(user)
                .build();
        creditCardRepository.save(creditCard);
        Page<CreditCard> creditCardByUser = creditCardRepository.findCreditCardByUser(user, Pageable.unpaged());
        assertNotNull(creditCardByUser);
    }
}