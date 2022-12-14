package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.payment.PaymentOverview;
import am.itspace.townrestaurantscommon.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentMapper {

    @Mapping(source = "payment.order", target = "orderOverview")
    @Mapping(source = "payment.user", target = "userOverview")
    PaymentOverview mapToDto(Payment payment);

    List<PaymentOverview> mapToDto(List<Payment> payments);
}

