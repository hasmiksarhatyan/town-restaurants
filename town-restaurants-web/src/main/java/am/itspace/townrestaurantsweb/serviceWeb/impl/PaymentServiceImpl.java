package am.itspace.townrestaurantsweb.serviceWeb.impl;

import am.itspace.townrestaurantscommon.dto.creditCard.CreateCreditCardDto;
import am.itspace.townrestaurantscommon.dto.payment.PaymentOverview;
import am.itspace.townrestaurantscommon.entity.*;
import am.itspace.townrestaurantscommon.mapper.PaymentMapper;
import am.itspace.townrestaurantscommon.repository.CreditCardRepository;
import am.itspace.townrestaurantscommon.repository.PaymentRepository;
import am.itspace.townrestaurantsweb.serviceWeb.CreditCardService;
import am.itspace.townrestaurantsweb.serviceWeb.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final CreditCardRepository creditCardRepository;
    private final CreditCardService creditCardService;
    private final PaymentMapper paymentMapper;


    @Override
    public Page<PaymentOverview> getPayments(Pageable pageable) {
        List<Payment> payments = paymentRepository.findAll();
        List<PaymentOverview> paymentOverviews = paymentMapper.mapToDto(payments);
        return new PageImpl<>(paymentOverviews);
    }

    @Override
    public void addPayment(Order order, User user) {
        Payment payment = Payment.builder()
                .paymentCreateDate(LocalDateTime.now())
                .user(user)
                .order(order)
                .totalAmount(order.getTotalPrice())
                .build();
        if (order.getPaymentOption() == PaymentOption.CREDIT_CARD) {
            CreateCreditCardDto cardDto = new CreateCreditCardDto();
            if (!creditCardRepository.existsByCardNumber(cardDto.getCardNumber())) {
                creditCardService.addCreditCard(cardDto, user);
                payment.setPaymentStatus(PaymentStatus.PROCESSING);
            }
        } else {
            payment.setPaymentStatus(PaymentStatus.UNPAID);
        }
        paymentRepository.save(payment);
    }


    @Override
    public void delete(int id) {
        if (!paymentRepository.existsById(id)) {
            throw new IllegalStateException();
        }
        paymentRepository.deleteById(id);
    }
}
