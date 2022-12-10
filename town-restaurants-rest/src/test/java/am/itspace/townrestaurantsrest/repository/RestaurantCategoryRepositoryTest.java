package am.itspace.townrestaurantsrest.repository;

import am.itspace.townrestaurantscommon.entity.RestaurantCategory;
import am.itspace.townrestaurantscommon.repository.RestaurantCategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static am.itspace.townrestaurantsrest.parameters.MockData.getRestaurantCategory;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class RestaurantCategoryRepositoryTest {

    @Autowired
    private RestaurantCategoryRepository restaurantCategoryRepository;

    @Test
    void existsByName() {
        RestaurantCategory restaurantCategory = getRestaurantCategory();
        restaurantCategoryRepository.save(restaurantCategory);
        boolean expected = restaurantCategoryRepository.existsByName(restaurantCategory.getName());
        assertTrue(expected);
    }
}