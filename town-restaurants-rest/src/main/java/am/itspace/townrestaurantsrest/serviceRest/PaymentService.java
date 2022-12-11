package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.dto.FetchRequestDto;
import am.itspace.townrestaurantscommon.dto.payment.PaymentOverview;
import am.itspace.townrestaurantscommon.entity.Order;
import am.itspace.townrestaurantscommon.entity.Payment;

import java.util.List;

public interface PaymentService {

    void delete(int id);

    void addPayment(Order order);

    List<PaymentOverview> getAll();

    List<Payment> getPaymentsList(FetchRequestDto fetchRequestDto);
}


