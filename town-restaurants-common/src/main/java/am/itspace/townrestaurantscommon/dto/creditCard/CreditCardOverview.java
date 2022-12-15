package am.itspace.townrestaurantscommon.dto.creditCard;

import am.itspace.townrestaurantscommon.dto.user.UserOverview;
import am.itspace.townrestaurantscommon.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardOverview {

    private Integer id;
    private String cardNumber;
    private String cardHolder;
    private LocalDate cardExpiresAt;
    private String cvv;
    private UserOverview userOverview;
}
