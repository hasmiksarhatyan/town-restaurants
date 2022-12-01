package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    boolean existsByName(String name);
}
