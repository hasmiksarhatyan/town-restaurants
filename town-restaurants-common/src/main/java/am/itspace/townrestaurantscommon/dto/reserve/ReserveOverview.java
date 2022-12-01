package am.itspace.townrestaurantscommon.dto.reserve;

import am.itspace.townrestaurantscommon.entity.Restaurant;
import am.itspace.townrestaurantscommon.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReserveOverview {

    private Integer id;
    private LocalDateTime reservedAt;
    private LocalDate reservedFor;
    private Restaurant restaurant;
    private User user;
    private int hostCount;
}
