package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.entity.Basket;
import am.itspace.townrestaurantscommon.entity.Product;
import am.itspace.townrestaurantscommon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Integer> {

    boolean existsByProduct(Product product);

    boolean existsByProductId(int id);

    Basket findBasketByProductId(int id);

    List<Product> findAllByProductId(int id);

    Page<Basket> findBasketByUser(User user, Pageable pageable);

    Basket findByProductAndUser(Product product, User user);

    Optional<Basket> findByUser(User user);
}
