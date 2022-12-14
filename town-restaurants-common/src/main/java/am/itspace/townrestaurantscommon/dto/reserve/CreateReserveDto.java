package am.itspace.townrestaurantscommon.dto.reserve;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateReserveDto {

//    @NotBlank
    private String reservedDate;

//    @NotBlank
    private String reservedTime;

    @NotNull
    private int peopleCount;

    @Schema(example = "+37499112233",
            pattern = "^[+]{1}[0-9]{12}$")
    @Size(min = 12, max = 12, message = "Phone number should start with '+' character and have 11 numbers after it.")
    private String phoneNumber;

    private Integer restaurantId;
}
