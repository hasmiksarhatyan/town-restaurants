package am.itspace.townrestaurantscommon.dto.payment;

import am.itspace.townrestaurantscommon.entity.PaymentOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentDto {

    private double totalPrice;

    @NotBlank(message = "Payment option is mandatory")
    private PaymentOption paymentOption;
}
