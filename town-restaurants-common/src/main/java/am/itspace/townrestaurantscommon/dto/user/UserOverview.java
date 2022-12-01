package am.itspace.townrestaurantscommon.dto.user;

import am.itspace.townrestaurantscommon.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOverview {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}
