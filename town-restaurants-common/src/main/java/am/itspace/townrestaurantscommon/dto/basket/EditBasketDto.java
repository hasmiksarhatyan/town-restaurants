package am.itspace.townrestaurantscommon.dto.basket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditBasketDto {

    private double quantity;

    @NotNull(message = "Product is mandatory")
    private Integer productId;
}
