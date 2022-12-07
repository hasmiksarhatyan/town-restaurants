package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantscommon.entity.Product;
import am.itspace.townrestaurantscommon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>, PagingAndSortingRepository<Product, Integer> {

    boolean existsByName(String name);

    List<Product> findAllById(int id);

    List<Product> findProductByUser(User user);

    List<Product> findProductsByRestaurant_Id(int id);

    Page<Product> findByOrderByPriceAsc(Pageable pageable);

    Page<Product> findByOrderByPriceDesc(Pageable pageable);

    Page<Product> findProductsByProductCategory_Id(int id, Pageable pageable);

    @Query("select name from Product name where name=:name")
    Page<Product> findByProductName(@Param("name") String name, Pageable pageReq);

    default Page<Product> findByProductName(ProductOverview productOverview, Pageable pageReq) {
        return findByProductName(productOverview.getName(), pageReq);
    }
}

