package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.payment.CreatePaymentDto;
import am.itspace.townrestaurantscommon.dto.payment.PaymentOverview;
import am.itspace.townrestaurantscommon.entity.Payment;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.4.1 (Amazon.com Inc.)"
)
@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public Payment mapToEntity(CreatePaymentDto createPaymentDto) {
        if ( createPaymentDto == null ) {
            return null;
        }

        Payment.PaymentBuilder payment = Payment.builder();

        payment.paymentOption( createPaymentDto.getPaymentOption() );
        payment.totalPrice( createPaymentDto.getTotalPrice() );

        return payment.build();
    }

    @Override
    public PaymentOverview mapToDto(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentOverview.PaymentOverviewBuilder paymentOverview = PaymentOverview.builder();

        paymentOverview.id( payment.getId() );
        paymentOverview.paymentOption( payment.getPaymentOption() );
        paymentOverview.paidAt( payment.getPaidAt() );
        paymentOverview.user( payment.getUser() );

        return paymentOverview.build();
    }

    @Override
    public List<PaymentOverview> mapToDto(List<Payment> payments) {
        if ( payments == null ) {
            return null;
        }

        List<PaymentOverview> list = new ArrayList<PaymentOverview>( payments.size() );
        for ( Payment payment : payments ) {
            list.add( mapToDto( payment ) );
        }

        return list;
    }

    @Override
    public List<PaymentOverview> mapToDto(Page<Payment> payments) {
        if ( payments == null ) {
            return null;
        }

        List<PaymentOverview> list = new ArrayList<PaymentOverview>();
        for ( Payment payment : payments ) {
            list.add( mapToDto( payment ) );
        }

        return list;
    }
}
