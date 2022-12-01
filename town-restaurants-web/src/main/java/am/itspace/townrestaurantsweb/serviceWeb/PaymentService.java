package am.itspace.townrestaurantsweb.serviceWeb;

import am.itspace.townrestaurantscommon.dto.creditCard.CreateCreditCardDto;
import am.itspace.townrestaurantscommon.dto.payment.CreatePaymentDto;
import am.itspace.townrestaurantscommon.dto.payment.PaymentOverview;
import am.itspace.townrestaurantscommon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PaymentService {

    Page<PaymentOverview> getPayments(Pageable pageable);

    boolean addPayment(CreatePaymentDto dto, CreateCreditCardDto creditCardDto, User user);

    void delete(int id);
}


