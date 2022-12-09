package am.itspace.townrestaurantsrest.repository;

import am.itspace.townrestaurantscommon.entity.*;
import am.itspace.townrestaurantscommon.repository.EventRepository;
import am.itspace.townrestaurantscommon.repository.RestaurantCategoryRepository;
import am.itspace.townrestaurantscommon.repository.RestaurantRepository;
import am.itspace.townrestaurantscommon.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static am.itspace.townrestaurantsrest.parameters.MockData.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantCategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @AfterEach
    void tearDown() {
        eventRepository.deleteAll();
    }

    @Test
    void findEventsByRestaurant_Id() {
        categoryRepository.save(getRestaurantCategory());
        userRepository.save(getUser());
        Restaurant restaurant = getRestaurant();
        restaurantRepository.save(restaurant);
        Event event = getEvent();
        eventRepository.save(event);
        List<Event> events = eventRepository.findEventsByRestaurant_Id(restaurant.getId());
        assertTrue(events.contains(event));
    }

    @Test
    void existsByName() {
        RestaurantCategory category = RestaurantCategory.builder()
                .name("aa")
                .build();
        categoryRepository.save(category);
        User user = User.builder()
                .email("yan@gmail.com")
                .firstName("Victoria")
                .password(passwordEncoder.encode("password"))
                .role(Role.MANAGER)
                .lastName("Yan")
                .build();
        userRepository.save(user);
        Restaurant restaurant = Restaurant.builder()
                .name("Limone")
                .address("P")
                .phone("099112233")
                .deliveryPrice(200.0)
                .email("limone@mail.com")
                .restaurantCategory(category)
                .user(user)
                .build();
        restaurantRepository.save(restaurant);
        Event event = Event.builder()
                .name("Mexican party")
                .restaurant(restaurant)
                .build();
        eventRepository.save(event);
        boolean expected = eventRepository.existsByName("Limone");
        assertTrue(expected);
    }
}