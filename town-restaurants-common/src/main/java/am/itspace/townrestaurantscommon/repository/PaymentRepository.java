package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer>, PagingAndSortingRepository<Payment, Integer> {

    Page<Payment> findByUserId(int id, Pageable pageable);
}
