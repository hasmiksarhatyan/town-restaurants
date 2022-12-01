package am.itspace.townrestaurantsrest.repository;

import am.itspace.townrestaurantscommon.entity.Product;
import am.itspace.townrestaurantscommon.entity.ProductCategory;
import am.itspace.townrestaurantscommon.entity.Restaurant;
import am.itspace.townrestaurantscommon.repository.ProductCategoryRepository;
import am.itspace.townrestaurantscommon.repository.ProductRepository;
import am.itspace.townrestaurantscommon.repository.RestaurantRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    void findAllById() {
        Product product = Product.builder()
                .name("Pizza")
                .price(2000.0)
                .build();
        productRepository.save(product);
        List<Product> allByCategoryId = productRepository.findAllById(product.getId());
        assertNotNull(allByCategoryId);
    }

    @Test
    void findProductsByProductCategory_Id() {
        ProductCategory category = ProductCategory.builder()
                .name("Hot dish")
                .build();
        Product product = Product.builder()
                .name("Pizza")
                .price(2000.0)
                .build();
        productRepository.save(product);
        Page<Product> productCategory = productRepository.findProductsByProductCategory_Id(category.getId(), Pageable.unpaged());
        assertNotNull(productCategory);
    }

    @Test
    void findByOrderByPriceAsc() {
        Product product = Product.builder()
                .name("Pizza")
                .price(2000.0)
                .build();
        productRepository.save(product);
        Page<Product> productCategory = productRepository.findByOrderByPriceAsc(Pageable.unpaged());
        assertNotNull(productCategory);
    }

    @Test
    void findByOrderByPriceDesc() {
        Product product = Product.builder()
                .name("Pizza")
                .price(2000.0)
                .build();
        productRepository.save(product);
        Page<Product> productCategory = productRepository.findByOrderByPriceDesc(Pageable.unpaged());
        assertNotNull(productCategory);
    }

    @Test
    void findProductsByRestaurant_Id() {
        Restaurant restaurant = Restaurant.builder()
                .name("Limone")
                .address("Tamanayan")
                .email("limone@gmail.com")
                .phone("099112233")
                .deliveryPrice(2000.0)
                .build();
        restaurantRepository.save(restaurant);
        Product product = Product.builder()
                .name("Pizza")
                .restaurant(restaurant)
                .price(2000.0)
                .build();
        productRepository.save(product);
        List<Product> products = productRepository.findProductsByRestaurant_Id(restaurant.getId());
        assertFalse(products.isEmpty());
    }

    @Test
    void existsByName() {
        Product product = Product.builder()
                .name("Pizza")
                .price(2000.0)
                .build();
        productRepository.save(product);
        boolean expected = productRepository.existsByName("Pizza");
        assertTrue(expected);
    }
}