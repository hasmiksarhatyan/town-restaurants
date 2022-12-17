package am.itspace.townrestaurantscommon.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDto {

    @NotBlank(message = "Name is mandatory")
    @Schema(description = "The length of name should be between 2 and 15.",
            example = "Pizza",
            minLength = 2,
            maxLength = 15,
            pattern = "^[A-Za-z]{2,15}$")
    private String name;

    @NotBlank(message = "Description is mandatory")
    @Schema(description = "Description length must be between 10 and 30 characters",
            minLength = 10,
            maxLength = 30)
    private String description;

    @NotBlank(message = "Price is mandatory")
    @Schema(description = "Price must consist of numbers only")
    private Double price;

    private Integer productCategoryId;

    private Integer restaurantId;

    private List<String> pictures;
}

