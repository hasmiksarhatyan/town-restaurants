package am.itspace.townrestaurantscommon.dto.payment;

import am.itspace.townrestaurantscommon.dto.order.OrderOverview;
import am.itspace.townrestaurantscommon.dto.user.UserOverview;
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
    private double totalAmount;
    private LocalDateTime paidAt;
    private LocalDateTime paymentCreateDate;
    private String paymentStatus;
    private OrderOverview orderOverview;
    private UserOverview userOverview;
}