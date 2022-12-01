package am.itspace.townrestaurantscommon.dto.restaurantCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantCategoryOverview {

    private Integer id;
    private String name;
}