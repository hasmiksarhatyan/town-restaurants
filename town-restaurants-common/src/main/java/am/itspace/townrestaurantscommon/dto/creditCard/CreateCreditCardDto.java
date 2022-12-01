package am.itspace.townrestaurantscommon.dto.creditCard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCreditCardDto {

    @NotBlank(message = "Card number is mandatory")
    private String cardNumber;

    @NotBlank(message = "Name is mandatory")
    private String cardHolder;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate cardExpiresAt;

    @NotBlank(message = "CVV is mandatory")
    private String cvv;
}
