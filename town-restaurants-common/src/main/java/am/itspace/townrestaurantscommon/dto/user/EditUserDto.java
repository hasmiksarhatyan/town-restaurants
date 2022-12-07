package am.itspace.townrestaurantscommon.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditUserDto {

    @NotBlank(message = "Name is mandatory")
    @Schema(description = "Name's length should be between 3 and 15 and name should contain only letters.",
            example = "Hayk",
            minLength = 3,
            maxLength = 15,
            pattern = "^[A-Za-z]{2,15}$")
    private String firstName;

    @Schema(description = "Lastname's length should be between 3 and 15 and name should contain only letters.",
            example = "Sargsyan",
            minLength = 3,
            maxLength = 15,
            pattern = "^[A-Za-z]{2,15}$")
    private String lastName;
}