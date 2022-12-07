package am.itspace.townrestaurantscommon.dto.creditCard;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCreditCardDto {

    @NotBlank(message = "Card number is mandatory")
    @Schema(pattern = "^(5[1-5][0-9]{14}|2(22[1-9][0-9]{12}|2[3-9][0-9]{13}|[3-6][0-9]{14}|7[0-1][0-9]{13}|720[0-9]{12}))$")
    @Size(min = 12, max = 12, message = "Credit card should have only numbers.")
    private String cardNumber;

    @NotBlank
    @Schema(example = "Hayk Sargsyan",
            minLength = 2,
            maxLength = 30,
            pattern = "^[A-Za-z]{2,30}$")
    private String cardHolder;

    @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate cardExpiresAt;

    @NotBlank(message = "CVV is mandatory")
    private String cvv;
}
