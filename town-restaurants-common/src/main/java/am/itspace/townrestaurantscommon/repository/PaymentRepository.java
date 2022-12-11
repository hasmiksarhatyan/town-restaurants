package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
