package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.entity.Payment;
import am.itspace.townrestaurantscommon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer>, PagingAndSortingRepository<Payment, Integer> {

    List<Payment> findAllByUser(User user);
}
