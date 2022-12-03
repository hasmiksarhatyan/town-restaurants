package am.itspace.townrestaurantscommon.dto.reserve;

import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantscommon.dto.user.UserOverview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReserveOverview {

    private Integer id;
    private LocalDateTime reservedAt;
    private LocalDate reservedDate;
    private LocalTime reservedTime;
    private int peopleCount;
    private String phoneNumber;
    private String status;
    private RestaurantOverview restaurantOverview;
    private UserOverview userOverview;
}
