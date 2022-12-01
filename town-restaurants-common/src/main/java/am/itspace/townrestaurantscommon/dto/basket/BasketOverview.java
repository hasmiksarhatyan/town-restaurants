package am.itspace.townrestaurantscommon.dto.basket;

import am.itspace.townrestaurantscommon.entity.Product;
import am.itspace.townrestaurantscommon.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasketOverview {

    private Integer id;
    private double quantity;
    private Product product;
    private User user;
}
