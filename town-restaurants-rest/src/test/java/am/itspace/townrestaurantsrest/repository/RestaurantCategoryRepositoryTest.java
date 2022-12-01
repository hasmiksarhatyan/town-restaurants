package am.itspace.townrestaurantsrest.repository;

import am.itspace.townrestaurantscommon.entity.RestaurantCategory;
import am.itspace.townrestaurantscommon.repository.RestaurantCategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RestaurantCategoryRepositoryTest {

    @Autowired
    private RestaurantCategoryRepository restaurantCategoryRepository;

    @Test
    void existsByName() {
        String name = "Shoe";
        RestaurantCategory category = RestaurantCategory.builder()
                .name(name)
                .build();
        restaurantCategoryRepository.save(category);
        boolean expected = restaurantCategoryRepository.existsByName(name);
        assertTrue(expected);
    }
}