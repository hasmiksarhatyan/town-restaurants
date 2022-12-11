package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.dto.order.OrderOverview;
import am.itspace.townrestaurantscommon.entity.Order;
import am.itspace.townrestaurantscommon.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer>, PagingAndSortingRepository<Order, Integer> {

    boolean existsByAdditionalAddressAndProducts(String additionalAddress, List<Product> products);

    @Query("select additionalAddress from Order additionalAddress where additionalAddress=:additionalAddress")
    Page<Order> findByAdditionalAddress(@Param("additionalAddress") String additionalAddress, Pageable pageReq);

    default Page<Order> findByAdditionalAddress(OrderOverview orderOverview, Pageable pageReq) {
        return findByAdditionalAddress(orderOverview.getAdditionalAddress(), pageReq);
    }
//    , PagingAndSortingRepository<Order, Integer>
}
