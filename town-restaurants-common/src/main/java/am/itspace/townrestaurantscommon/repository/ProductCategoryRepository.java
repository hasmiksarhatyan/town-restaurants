package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.dto.productCategory.ProductCategoryOverview;
import am.itspace.townrestaurantscommon.entity.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer>, PagingAndSortingRepository<ProductCategory, Integer> {

    boolean existsByName(String name);

    @Query("select name from ProductCategory name where name=:name")
    Page<ProductCategory> findByProductCategoryName(@Param("name") String name, Pageable pageReq);

    default Page<ProductCategory> findByProductCategoryName(ProductCategoryOverview productCategoryOverview, Pageable pageReq) {
        return findByProductCategoryName(productCategoryOverview.getName(), pageReq);
    }
}
