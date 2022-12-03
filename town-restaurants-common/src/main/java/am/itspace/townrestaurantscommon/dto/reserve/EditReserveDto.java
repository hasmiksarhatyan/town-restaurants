package am.itspace.townrestaurantscommon.dto.reserve;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditReserveDto {

    private String reservedDate;
    private String reservedTime;
    private int peopleCount;
    private String status;
    private String phoneNumber;

}
