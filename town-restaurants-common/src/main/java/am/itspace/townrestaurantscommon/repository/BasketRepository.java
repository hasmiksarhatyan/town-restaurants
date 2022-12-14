package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.entity.Basket;
import am.itspace.townrestaurantscommon.entity.Product;
import am.itspace.townrestaurantscommon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Integer>, PagingAndSortingRepository<Basket, Integer> {

    boolean existsByProductId(int id);

    Basket findBasketByProductId(int id);

    Optional<Basket> findByUser(User user);

    Page<Basket> findAll(Pageable pageable);

    List<Basket> findBasketByUser(User user);

    boolean existsByProduct(Product product);

    Basket findByProductAndUser(Product product, User user);

    Page<Basket> findAllByUser(User user, Pageable pageable);

    boolean existsByProductAndUser(Product product, User user);
}