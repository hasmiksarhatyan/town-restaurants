package am.itspace.townrestaurantscommon.dto.restaurant;

import am.itspace.townrestaurantscommon.dto.FileDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestaurantRequestDto {

    private CreateRestaurantDto createRestaurantDto;
    private FileDto fileDto;
}
