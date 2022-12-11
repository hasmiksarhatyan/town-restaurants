package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.FetchRequestDto;
import am.itspace.townrestaurantscommon.dto.creditCard.CreateCreditCardDto;
import am.itspace.townrestaurantscommon.dto.payment.PaymentOverview;
import am.itspace.townrestaurantscommon.entity.*;
import am.itspace.townrestaurantscommon.mapper.PaymentMapper;
import am.itspace.townrestaurantscommon.mapper.UserMapper;
import am.itspace.townrestaurantscommon.repository.CreditCardRepository;
import am.itspace.townrestaurantscommon.repository.PaymentRepository;
import am.itspace.townrestaurantsrest.exception.AuthenticationException;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;
import am.itspace.townrestaurantsrest.exception.Error;
import am.itspace.townrestaurantsrest.serviceRest.CreditCardService;
import am.itspace.townrestaurantsrest.serviceRest.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static am.itspace.townrestaurantsrest.exception.Error.NEEDS_AUTHENTICATION;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final UserMapper userMapper;
    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;
    private final CreditCardService creditCardService;
    private final CreditCardRepository creditCardRepository;
    private final SecurityContextServiceImpl securityContextService;

    @Override
    public List<PaymentOverview> getAll() {
        List<Payment> payments = paymentRepository.findAll();
        if (payments.isEmpty()) {
            log.info("Payment not found");
            throw new EntityNotFoundException(Error.PAYMENT_NOT_FOUND);
        }
        log.info("Payments successfully found");
        return paymentMapper.mapToDto(payments);
    }

    @Override
    public List<Payment> getPaymentsList(FetchRequestDto dto) {
        try {
            User user = securityContextService.getUserDetails().getUser();
            PageRequest pageReq = PageRequest.of(dto.getPage(), dto.getSize(), Sort.Direction.fromString(dto.getSortDir()), dto.getSort());
            Page<Payment> payments = paymentRepository.findByUser(userMapper.mapToResponseDto(user), pageReq);
            if (payments.isEmpty()) {
                log.info("Payment not found");
                throw new EntityNotFoundException(Error.PAYMENT_NOT_FOUND);
            }
            log.info("Payments successfully found");
            return payments.getContent();
        } catch (ClassCastException e) {
            throw new AuthenticationException(NEEDS_AUTHENTICATION);
        }
    }

    @Override
    public void addPayment(Order order) {
        try {
            User user = securityContextService.getUserDetails().getUser();
            Payment payment = Payment.builder()
                    .paymentCreateDate(LocalDateTime.now())
                    .user(user)
                    .order(order)
                    .totalAmount(order.getTotalPrice())
                    .build();
            if (order.getPaymentOption() == PaymentOption.CREDIT_CARD) {
                CreateCreditCardDto cardDto = new CreateCreditCardDto();
                if (!creditCardRepository.existsByCardNumber(cardDto.getCardNumber())) {
                    creditCardService.save(cardDto);
                    payment.setPaymentStatus(PaymentStatus.PROCESSING);
                }
            } else {
                payment.setPaymentStatus(PaymentStatus.UNPAID);
            }
            paymentRepository.save(payment);
            log.info("The payment was successfully stored in the database {}", payment.getTotalAmount());
        } catch (ClassCastException e) {
            throw new AuthenticationException(NEEDS_AUTHENTICATION);
        }
    }

    @Override
    public void delete(int id) {
        if (!paymentRepository.existsById(id)) {
            log.info("Payment not found");
            throw new EntityNotFoundException(Error.PAYMENT_NOT_FOUND);
        }
        paymentRepository.deleteById(id);
        log.info("The payment has been successfully deleted");
    }
}