package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.entity.Product;
import am.itspace.townrestaurantscommon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>, PagingAndSortingRepository<Product, Integer> {

    boolean existsByName(String name);

    List<Product> findAllById(int id);

    List<Product> findProductByUser(User user);

    List<Product> findProductsByRestaurant_Id(int id);

    Page<Product> findByOrderByPriceAsc(Pageable pageable);

    Page<Product> findByOrderByPriceDesc(Pageable pageable);

    Page<Product> findProductsByProductCategory_Id(int id, Pageable pageable);

    Page<Product> findAllByRestaurantId(int id, Pageable pageable);

    Page<Product> findAllByUser(User user, Pageable pageable);
}

