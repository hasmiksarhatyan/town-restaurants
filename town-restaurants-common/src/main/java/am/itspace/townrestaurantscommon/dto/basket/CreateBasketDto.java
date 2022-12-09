package am.itspace.townrestaurantscommon.dto.basket;

import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantscommon.dto.user.UserOverview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBasketDto {

    private Integer id;
    private double quantity;
    private Integer productId;
    private UserOverview userOverview;
}
