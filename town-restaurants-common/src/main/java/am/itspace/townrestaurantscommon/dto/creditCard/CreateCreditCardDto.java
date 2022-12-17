package am.itspace.townrestaurantscommon.dto.creditCard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCreditCardDto {

    @CreditCardNumber
    private String cardNumber;

    @NotBlank(message = "Name is mandatory")
    private String cardHolder;

    @NotNull
    @Future(message = "Expired Credit Card")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate cardExpiresAt;

    @NotBlank(message = "CVV is mandatory")
    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    private String cvv;
}
