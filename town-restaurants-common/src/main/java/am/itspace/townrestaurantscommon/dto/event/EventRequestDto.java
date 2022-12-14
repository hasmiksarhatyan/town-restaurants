package am.itspace.townrestaurantscommon.dto.event;

import am.itspace.townrestaurantscommon.dto.FileDto;
import lombok.Data;

@Data
public class EventRequestDto {

    private CreateEventDto createEventDto;
    private FileDto fileDto;
}
