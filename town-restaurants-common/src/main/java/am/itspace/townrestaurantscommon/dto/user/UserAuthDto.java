package am.itspace.townrestaurantscommon.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthDto {

    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 5, max = 8, message = "Password must be at least 5 characters")
    private String password;
}
