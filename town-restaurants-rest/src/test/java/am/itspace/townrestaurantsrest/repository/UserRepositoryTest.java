package am.itspace.townrestaurantsrest.repository;

import am.itspace.townrestaurantscommon.entity.Role;
import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        String email = "yan@gmail.com";
        User user = User.builder()
                .email(email)
                .firstName("Hayk")
                .password(passwordEncoder.encode("password"))
                .role(Role.MANAGER)
                .lastName("Yan")
                .build();
        userRepository.save(user);
        Optional<User> byEmail = userRepository.findByEmail(email);
        assertEquals(user, byEmail.get());
    }

    @Test
    void existsByEmail() {
        String email = "yan@gmail.com";
        User user = User.builder()
                .email(email)
                .firstName("Hayk")
                .password(passwordEncoder.encode("password"))
                .role(Role.MANAGER)
                .lastName("Yan")
                .build();
        userRepository.save(user);
        boolean expected = userRepository.existsByEmail(email);
        assertTrue(expected);
    }
}