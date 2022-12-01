package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
