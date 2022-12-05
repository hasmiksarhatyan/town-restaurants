package am.itspace.townrestaurantscommon.dto.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationTokenDto {

    @NotBlank(message = "Token is mandatory")
    private String plainToken;
}
