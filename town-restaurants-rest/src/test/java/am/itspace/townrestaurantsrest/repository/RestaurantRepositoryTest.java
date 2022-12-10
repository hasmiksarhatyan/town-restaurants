package am.itspace.townrestaurantsrest.repository;

import am.itspace.townrestaurantscommon.entity.Restaurant;
import am.itspace.townrestaurantscommon.entity.Role;
import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.repository.RestaurantRepository;
import am.itspace.townrestaurantscommon.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import static am.itspace.townrestaurantsrest.parameters.MockData.getRestaurant;
import static am.itspace.townrestaurantsrest.parameters.MockData.getUser;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        restaurantRepository.deleteAll();
    }

    @Test
    void existsByEmailIgnoreCase() {
        User user = getUser();
        userRepository.save(user);
        Restaurant restaurant = getRestaurant();
        restaurantRepository.save(restaurant);
        boolean expected = restaurantRepository.existsByEmailIgnoreCase(restaurant.getEmail());
        assertTrue(expected);
    }

    @Test
    void findRestaurantsByUser() {
        User user = User.builder()
                .email("yan@gmail.com")
                .firstName("Hayk")
                .password(passwordEncoder.encode("password"))
                .role(Role.MANAGER)
                .lastName("Yan")
                .build();
        userRepository.save(user);
        Restaurant restaurant = Restaurant.builder()
                .name("Limone")
                .address("Tamanayan")
                .email("limone@gmail.com")
                .phone("099112233")
                .deliveryPrice(2000.0)
                .build();
        restaurantRepository.save(restaurant);
        Page<Restaurant> restaurantsByUser = restaurantRepository.findRestaurantsByUser(user, Pageable.unpaged());
        assertNotNull(restaurantsByUser);
    }
}