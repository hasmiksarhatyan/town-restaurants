package am.itspace.townrestaurantscommon.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDto {

    @NotBlank(message = "Password is mandatory")
    private String oldPassword;

    @NotBlank(message = "Password is mandatory")
    private String newPassword1;
    private String newPassword2;
}