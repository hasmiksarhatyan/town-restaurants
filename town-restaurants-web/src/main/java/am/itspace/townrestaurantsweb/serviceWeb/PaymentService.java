package am.itspace.townrestaurantsweb.serviceWeb;

import am.itspace.townrestaurantscommon.dto.creditCard.CreateCreditCardDto;
import am.itspace.townrestaurantscommon.dto.payment.EditPaymentDto;
import am.itspace.townrestaurantscommon.dto.payment.PaymentOverview;
import am.itspace.townrestaurantscommon.entity.Order;
import am.itspace.townrestaurantscommon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {

    void delete(int id);

    PaymentOverview getById(int id);

    void editPayment(EditPaymentDto dto, int id);

    void addPayment(Order order, CreateCreditCardDto cardDto, User user);

    Page<PaymentOverview> getPayments(Pageable pageable);

    Page<PaymentOverview> getPaymentsByUser(int id, Pageable pageable);
}


