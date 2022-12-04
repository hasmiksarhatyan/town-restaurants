package am.itspace.townrestaurantscommon.dto.user;


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
    private String firstName;

    private String lastName;

    @Email
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 5, max = 30, message = "Password must be at least 5 characters")
    private String password;

    private String verifyToken;

    private boolean enabled;
}