package am.itspace.townrestaurantsweb.serviceWeb;

import am.itspace.townrestaurantscommon.dto.payment.PaymentOverview;
import am.itspace.townrestaurantscommon.entity.Order;
import am.itspace.townrestaurantscommon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {

    void delete(int id);

    void addPayment(Order order, User user);

    Page<PaymentOverview> getPayments(Pageable pageable);
}


