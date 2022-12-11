package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.dto.payment.PaymentOverview;
import am.itspace.townrestaurantscommon.dto.user.UserOverview;
import am.itspace.townrestaurantscommon.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Query("select user from Basket user where user=:userOverview")
    Page<Payment> findByUser(@Param("userOverview") UserOverview userOverview, Pageable pageReq);

    default Page<Payment> findByUser(PaymentOverview paymentOverview, Pageable pageReq) {
        return findByUser(paymentOverview.getUserOverview(), pageReq);
    }
}
