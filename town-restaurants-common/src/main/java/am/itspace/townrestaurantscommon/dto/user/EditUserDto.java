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
public class EditUserDto {

    @NotBlank(message = "Name is mandatory")
    private String firstName;
    private String lastName;
}