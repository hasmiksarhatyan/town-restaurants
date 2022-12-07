package am.itspace.townrestaurantscommon.dto.restaurant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRestaurantDto {

    @NotBlank(message = "Name is mandatory")
    @Schema(description = "Name's length should be between 2 and 15.",
            example = "Limone",
            minLength = 3,
            maxLength = 15,
            pattern = "^[A-Za-z]{2,15}$")
    private String name;

    private String address;

    @Email
    @Schema(example = "example@gmail.com")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Schema(example = "+37499112233",
            pattern = "^[+]{1}[0-9]{12}$")
    @Size(min = 12, max = 12, message = "Phone number should start with '+' character and have 11 numbers after it.")
    private String phone;

    @NotBlank(message = "Category is mandatory")
    private Integer restaurantCategoryId;

    @NotBlank(message = "Price is mandatory")
    @Schema(description = "Price must consist of numbers only")
    private Double deliveryPrice;

    private List<String> pictures;
}