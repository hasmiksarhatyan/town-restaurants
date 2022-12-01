package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.payment.CreatePaymentDto;
import am.itspace.townrestaurantscommon.dto.payment.PaymentOverview;
import am.itspace.townrestaurantscommon.entity.Payment;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    Payment mapToEntity(CreatePaymentDto createPaymentDto);

    PaymentOverview mapToDto(Payment payment);

    List<PaymentOverview> mapToDto(List<Payment> payments);

    List<PaymentOverview> mapToDto(Page<Payment> payments);
}

