package am.itspace.townrestaurantscommon.dto.restaurant;

import am.itspace.townrestaurantscommon.dto.restaurantCategory.RestaurantCategoryOverview;
import am.itspace.townrestaurantscommon.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantOverview {

    private Integer id;
    private String name;
    private String address;
    private String email;
    private String phone;
    private RestaurantCategoryOverview restaurantCategoryOverview;
    private Double deliveryPrice;
    private List<String> pictures;
    private User user;
}