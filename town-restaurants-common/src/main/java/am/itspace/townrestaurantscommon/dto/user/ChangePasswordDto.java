package am.itspace.townrestaurantscommon.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDto {

    private String oldPassword;
    private String newPassword1;
    private String newPassword2;
}