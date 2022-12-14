package am.itspace.townrestaurantscommon.dto.event;

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
public class CreateEventDto {

    @NotBlank(message = "Name is mandatory")
    @Schema(description = "The length of name should be between 3 and 15.",
            example = "Italian party",
            minLength = 3,
            maxLength = 15,
            pattern = "^[A-Za-z]{4,20}$")
    private String name;

    @NotBlank(message = "Description is mandatory")
    @Schema(description = "Description length must be between 15 and 70 characters",
            minLength = 15,
            maxLength = 70)
    private String description;

    @NotNull(message = "Price is mandatory")
    @Schema(description = "Price must consist of numbers only")
    private double price;

    @NotBlank(message = "Date is mandatory")
    private String eventDateTime;

    @NotNull(message = "Restaurant is mandatory")
    private Integer restaurantId;

    private List<String> pictures;
}
