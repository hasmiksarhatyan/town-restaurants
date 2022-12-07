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
public class UserAuthDto {

    @Email
    @Schema(example = "example@gmail.com")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Schema(example = "Example1234$")
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 20, message = "The length should be between 2 and 20 characters!")
    private String password;
}
