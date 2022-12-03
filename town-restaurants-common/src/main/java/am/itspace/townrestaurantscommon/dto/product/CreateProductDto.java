package am.itspace.townrestaurantscommon.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDto {

    @NotBlank(message = "Name is mandatory")
    private String name;

    private String description;

//    @NotBlank(message = "Price is mandatory")
    private Double price;

    private Integer productCategoryId;

    private Integer restaurantId;

    private List<String> pictures;
}

