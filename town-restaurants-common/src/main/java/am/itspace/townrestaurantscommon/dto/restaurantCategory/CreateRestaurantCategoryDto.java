package am.itspace.townrestaurantscommon.dto.restaurantCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRestaurantCategoryDto {

    @NotBlank(message = "Name is mandatory")
    private String name;
}
