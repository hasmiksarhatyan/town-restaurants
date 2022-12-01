package am.itspace.townrestaurantscommon.dto.product;

import am.itspace.townrestaurantscommon.dto.productCategory.ProductCategoryOverview;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantscommon.dto.user.UserOverview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOverview {

    private int id;
    private String name;
    private String description;
    private Double price;
    private ProductCategoryOverview productCategoryOverview;
    private RestaurantOverview restaurantOverview;
    private List<String> pictures;
    private UserOverview userOverview;
}