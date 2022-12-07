package am.itspace.townrestaurantscommon.dto.payment;

import am.itspace.townrestaurantscommon.entity.PaymentOption;
import io.swagger.v3.oas.annotations.media.Schema;
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
