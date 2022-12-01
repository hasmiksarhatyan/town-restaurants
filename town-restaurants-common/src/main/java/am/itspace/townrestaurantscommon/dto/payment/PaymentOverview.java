package am.itspace.townrestaurantscommon.dto.payment;

import am.itspace.townrestaurantscommon.entity.PaymentOption;
import am.itspace.townrestaurantscommon.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOverview {

    private int id;
    private PaymentOption paymentOption;
    private Double paymentAmount;
    private LocalDateTime paidAt;
    private User user;
}
