package am.itspace.townrestaurantscommon.dto;

import am.itspace.townrestaurantscommon.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationTokenDto {

    private String plainToken;
}
