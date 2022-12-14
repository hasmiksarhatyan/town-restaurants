package am.itspace.townrestaurantscommon.dto.restaurant;

import am.itspace.townrestaurantscommon.dto.FileDto;
import lombok.Data;

@Data
public class RestaurantRequestDto {

    private CreateRestaurantDto createRestaurantDto;
    private FileDto fileDto;
}
