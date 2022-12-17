package am.itspace.townrestaurantscommon.dto.user;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {

    @NotBlank(message = "Name is mandatory")
    @Schema(description = "The length of name should be between 3 and 15 and name should contain only letters.",
            example = "Hayk",
            minLength = 3,
            maxLength = 15,
            pattern = "^[A-Za-z]{2,15}$")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Schema(description = "The length of last name should be between 3 and 15 and name should contain only letters.",
            example = "Sargsyan",
            minLength = 3,
            maxLength = 15,
            pattern = "^[A-Za-z]{2,15}$")
    private String lastName;

    @Email
    @Schema(example = "example@gmail.com")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Schema(example = "Example1234$")
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 20, message = "The length should be between 8 and 20 characters!")
    private String password;

    private String verifyToken;

    private boolean enabled;
}