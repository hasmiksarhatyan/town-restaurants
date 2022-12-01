package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.entity.Product;
import am.itspace.townrestaurantscommon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findProductByUser(User user);

    List<Product> findAllById(int id);

    Page<Product> findProductsByProductCategory_Id(int id, Pageable pageable);

    Page<Product> findByOrderByPriceAsc(Pageable pageable);

    Page<Product> findByOrderByPriceDesc(Pageable pageable);

    List<Product> findProductsByRestaurant_Id(int id);

    boolean existsByName(String name);
}

