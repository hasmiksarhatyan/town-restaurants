package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer>, PagingAndSortingRepository<Payment, Integer> {

}
