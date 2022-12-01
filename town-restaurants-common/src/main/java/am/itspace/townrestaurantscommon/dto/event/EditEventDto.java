package am.itspace.townrestaurantscommon.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditEventDto {

    @NotBlank(message = "Name is mandatory")
    private String name;

    private String description;

    private double price;

    @NotBlank(message = "Date is mandatory")
    private String eventDateTime;

    private Integer restaurantId;

    private List<String> pictures;
}
