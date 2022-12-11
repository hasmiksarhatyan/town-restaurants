package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.dto.basket.BasketOverview;
import am.itspace.townrestaurantscommon.dto.user.UserOverview;
import am.itspace.townrestaurantscommon.entity.Basket;
import am.itspace.townrestaurantscommon.entity.Product;
import am.itspace.townrestaurantscommon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Integer> {

    boolean existsByProductId(int id);

    Basket findBasketByProductId(int id);

    Optional<Basket> findByUser(User user);

    ///nayel
    List<Product> findAllByProductId(int id);

    List<Basket> findBasketByUser(User user);

    boolean existsByProduct(Product product);

    Basket findByProductAndUser(Product product, User user);

    boolean existsByProductAndUser(Product product, User user);

    @Query("select user from Basket user where user=:userOverview")
    Page<Basket> findByUser(@Param("userOverview") UserOverview userOverview, Pageable pageReq);

    default Page<Basket> findByUser(BasketOverview basketOverview, Pageable pageReq) {
        return findByUser(basketOverview.getUserOverview(), pageReq);
    }
}