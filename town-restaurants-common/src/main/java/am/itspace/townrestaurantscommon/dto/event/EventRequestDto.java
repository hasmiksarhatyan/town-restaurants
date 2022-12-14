package am.itspace.townrestaurantscommon.dto.event;

import am.itspace.townrestaurantscommon.dto.FileDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventRequestDto {

    private CreateEventDto createEventDto;
    private FileDto fileDto;
}
