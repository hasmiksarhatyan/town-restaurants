package am.itspace.townrestaurantscommon.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditPaymentDto {

    private String paidAt;
    private String status;
}
