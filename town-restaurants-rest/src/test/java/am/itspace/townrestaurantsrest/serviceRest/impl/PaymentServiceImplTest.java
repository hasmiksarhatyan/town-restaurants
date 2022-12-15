package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.payment.PaymentOverview;
import am.itspace.townrestaurantscommon.entity.Payment;
import am.itspace.townrestaurantscommon.entity.PaymentStatus;
import am.itspace.townrestaurantscommon.mapper.PaymentMapper;
import am.itspace.townrestaurantscommon.repository.CreditCardRepository;
import am.itspace.townrestaurantscommon.repository.PaymentRepository;
import am.itspace.townrestaurantscommon.security.CurrentUser;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static am.itspace.townrestaurantsrest.parameters.MockData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentMapper paymentMapper;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    CreditCardServiceImpl creditCardService;

    @Mock
    CreditCardRepository creditCardRepository;

    @Mock
    SecurityContextServiceImpl securityContextService;

    @Test
    void shouldSavePayment() {
        //given
        var payment = getPayment();
        var order = getOrderForPayment();
        var creditCard = getCreditCard();
        CurrentUser currentUser = new CurrentUser(getUser());
        //when
        doReturn(currentUser).when(securityContextService).getUserDetails();
        doReturn(false).when(creditCardRepository).existsByCardNumber(creditCard.getCardNumber());
        creditCard.setUser(currentUser.getUser());
        creditCardService.save(creditCard);
        payment.setStatus(PaymentStatus.PROCESSING);
        paymentRepository.save(payment);
        paymentService.addPayment(order, creditCard);
        //then
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void shouldSavePaymentAsPaymentStatusIsUnpaid() {
        //given
        var order = getOrder();
        var payment = getPayment();
        var creditCard = getCreditCard();
        CurrentUser currentUser = new CurrentUser(getUser());
        //when
        doReturn(currentUser).when(securityContextService).getUserDetails();
        payment.setStatus(PaymentStatus.UNPAID);
        paymentRepository.save(payment);
        paymentService.addPayment(order, creditCard);
        //then
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void shouldGetAll() {
        //given
        var listOfPaymentsPage = getPagePayment();
        var expected = List.of(getPaymentOverview(), getPaymentOverview());
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(listOfPaymentsPage).when(paymentRepository).findAll(pageable);
        doReturn(expected).when(paymentMapper).mapToDto(anyList());
        List<PaymentOverview> actual = paymentService.getAll(1, 1, "name", "DESC");
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getAllShouldThrowException() {
        //given
        Page<Payment> empty = Page.empty();
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(empty).when(paymentRepository).findAll(pageable);
        //then
        assertThrows(EntityNotFoundException.class, () -> paymentService.getAll(1, 1, "name", "DESC"));
    }

    @Test
    void deleteSuccess() {
        //given
        int paymentId = 1;
        //when
        when(paymentRepository.existsById(paymentId)).thenReturn(true);
        paymentService.delete(paymentId);
        //then
        verify(paymentRepository).deleteById(paymentId);
    }

    @Test
    void shouldThrowExceptionAsPaymentNotFound() {
        //given
        int paymentId = 1;
        //when
        when(paymentRepository.existsById(paymentId)).thenReturn(false);
        //then
        assertThrows(EntityNotFoundException.class, () -> paymentService.delete(paymentId));
    }
}