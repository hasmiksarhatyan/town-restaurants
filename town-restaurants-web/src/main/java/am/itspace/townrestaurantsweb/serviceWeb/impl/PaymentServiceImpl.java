package am.itspace.townrestaurantsweb.serviceWeb.impl;

import am.itspace.townrestaurantscommon.dto.creditCard.CreateCreditCardDto;
import am.itspace.townrestaurantscommon.dto.payment.EditPaymentDto;
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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;
    private final CreditCardService creditCardService;
    private final CreditCardRepository creditCardRepository;

    @Override
    public Page<PaymentOverview> getPayments(Pageable pageable) {
        Page<Payment> payments = paymentRepository.findAll(pageable);
        if (!payments.isEmpty()) {
            log.info("Payments successfully found");
        }
        return payments.map(paymentMapper::mapToDto);
    }

    public Page<PaymentOverview> getPaymentsByUser(int id, Pageable pageable) {
        Page<Payment> payments = paymentRepository.findByUserId(id, pageable);
        if (!payments.isEmpty()) {
            log.info("Payments successfully found");
        }
        return payments.map(paymentMapper::mapToDto);
    }

    @Override
    public void addPayment(Order order, CreateCreditCardDto cardDto, User user) {
        Payment payment = Payment.builder()
                .paymentCreateDate(LocalDateTime.now())
                .user(user)
                .order(order)
                .totalAmount(order.getTotalPrice())
                .build();
        if (order.getPaymentOption() == PaymentOption.CREDIT_CARD) {
            if (!creditCardRepository.existsByCardNumber(cardDto.getCardNumber())) {
                creditCardService.addCreditCard(cardDto, user);
                payment.setStatus(PaymentStatus.PROCESSING);
            }
        } else {
            payment.setStatus(PaymentStatus.UNPAID);
        }
        paymentRepository.save(payment);
        log.info("The payment was successfully stored in the database {}", payment.getTotalAmount());
    }

    @Override
    public PaymentOverview getById(int id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new IllegalStateException("Something went wrong, try again!"));
        log.info("Payment successfully found");
        return paymentMapper.mapToDto(payment);
    }

    public void editPayment(EditPaymentDto dto, int id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new IllegalStateException("Something went wrong, try again!"));
        String status = dto.getStatus();
        if (status != null) {
            payment.setStatus(PaymentStatus.valueOf(status));
        }
        String paidAt = dto.getPaidAt();
        if (paidAt != null) {
            payment.setPaidAt(LocalDateTime.parse(paidAt));
        }
        log.info("The payment was successfully updated in the database {}", payment.getId());
        paymentRepository.save(payment);
    }

    @Override
    public void delete(int id) {
        if (!paymentRepository.existsById(id)) {
            throw new IllegalStateException("Payment not found!");
        }
        paymentRepository.deleteById(id);
        log.info("The payment has been successfully deleted");
    }
}
