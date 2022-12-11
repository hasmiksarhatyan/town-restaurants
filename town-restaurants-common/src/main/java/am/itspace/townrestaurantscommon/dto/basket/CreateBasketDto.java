package am.itspace.townrestaurantscommon.dto.basket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBasketDto {

    private double quantity;

    @NotBlank(message = "Product is mandatory")
    private Integer productId;
}
