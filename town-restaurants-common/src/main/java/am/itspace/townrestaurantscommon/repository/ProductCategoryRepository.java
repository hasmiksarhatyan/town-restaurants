package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer>, PagingAndSortingRepository<ProductCategory, Integer> {

    boolean existsByName(String name);
}
