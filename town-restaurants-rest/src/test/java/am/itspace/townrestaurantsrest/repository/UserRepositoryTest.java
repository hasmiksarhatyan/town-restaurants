package am.itspace.townrestaurantsrest.repository;

import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static am.itspace.townrestaurantsrest.parameters.MockData.getUser;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void findByEmail() {
        User user = getUser();
        userRepository.save(user);
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        assertNotNull(userOptional);
    }

    @Test
    void existsByEmail() {
        User user = getUser();
        userRepository.save(user);
        boolean expected = userRepository.existsByEmail(user.getEmail());
        assertTrue(expected);
    }

    @Test
    void existsByEmailIgnoreCase() {
        User user = getUser();
        userRepository.save(user);
        boolean expected = userRepository.existsByEmailIgnoreCase(user.getEmail());
        assertTrue(expected);
    }
}