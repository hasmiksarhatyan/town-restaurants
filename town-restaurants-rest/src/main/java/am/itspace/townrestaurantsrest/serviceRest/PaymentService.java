package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.dto.payment.PaymentOverview;
import am.itspace.townrestaurantscommon.entity.CreditCard;
import am.itspace.townrestaurantscommon.entity.Order;

import java.util.List;

public interface PaymentService {

    void delete(int id);

    void addPayment(Order order, CreditCard creditCard);

    List<PaymentOverview> getAllByUser();

    List<PaymentOverview> getAllPayments(int pageNo, int pageSize, String sortBy, String sortDir);
}


