package am.itspace.townrestaurantsrest.repository;

import am.itspace.townrestaurantscommon.entity.ProductCategory;
import am.itspace.townrestaurantscommon.repository.ProductCategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Test
    void existsByName() {
        ProductCategory category = ProductCategory.builder()
                .name("Hot dish")
                .build();
        productCategoryRepository.save(category);
        boolean expected = productCategoryRepository.existsByName("Hot dish");
        assertTrue(expected);
    }
}