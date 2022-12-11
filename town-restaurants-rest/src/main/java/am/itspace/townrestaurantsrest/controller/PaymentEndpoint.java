package am.itspace.townrestaurantsrest.controller;

import am.itspace.townrestaurantscommon.dto.FetchRequestDto;
import am.itspace.townrestaurantscommon.dto.payment.PaymentOverview;
import am.itspace.townrestaurantscommon.entity.Payment;
import am.itspace.townrestaurantscommon.mapper.UserMapper;
import am.itspace.townrestaurantsrest.api.PaymentApi;
import am.itspace.townrestaurantsrest.serviceRest.PaymentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentEndpoint implements PaymentApi {

    private final UserMapper userMapper;
    private final ModelMapper modelMapper;
    private final PaymentService paymentService;

    @Override
    @GetMapping
    public ResponseEntity<List<PaymentOverview>> getAll() {
        return ResponseEntity.ok(paymentService.getAll());
    }

    @Override
    @GetMapping("/pages")
    public ResponseEntity<List<PaymentOverview>> getAll(@Valid @RequestBody FetchRequestDto fetchRequestDto) {
        List<Payment> payments = paymentService.getPaymentsList(fetchRequestDto);
        return ResponseEntity.ok(payments.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        paymentService.delete(id);
        return ResponseEntity.ok().build();
    }

    private PaymentOverview convertToDto(Payment payment) {
        PaymentOverview paymentOverview = modelMapper.map(payment, PaymentOverview.class);
        paymentOverview.setUserOverview(userMapper.mapToResponseDto(payment.getUser()));
        return paymentOverview;
    }
}

