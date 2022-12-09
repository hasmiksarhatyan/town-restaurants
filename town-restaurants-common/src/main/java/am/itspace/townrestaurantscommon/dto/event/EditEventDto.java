package am.itspace.townrestaurantscommon.dto.event;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditEventDto {

    @NotBlank(message = "Name is mandatory")
    @Schema(description = "Name's length should be between 4 and 20.",
            example = "Italian party",
            minLength = 3,
            maxLength = 15,
            pattern = "^[A-Za-z]{4,20}$")
    private String name;

    @NotBlank(message = "Description is mandatory")
    @Schema(description = "Description length must be between 50 and 70 characters",
            minLength = 50,
            maxLength = 70)
    private String description;

    @NotBlank(message = "Price is mandatory")
    @Schema(description = "Price must consist of numbers only")
    private double price;

    private String eventDateTime;

    private Integer restaurantId;

    private List<String> pictures;

}
