package am.itspace.townrestaurantsweb.serviceWeb;

import am.itspace.townrestaurantscommon.dto.event.CreateEventDto;
import am.itspace.townrestaurantscommon.dto.event.EditEventDto;
import am.itspace.townrestaurantscommon.dto.event.EventOverview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface EventService {

    Page<EventOverview> findAll(Pageable pageable);

    Page<EventOverview> findEventsByRestaurantId(int id, Pageable pageable);

    void save(CreateEventDto eventDto, MultipartFile[] files) throws IOException;

    void editEvent(EditEventDto dto, int id, MultipartFile[] files) throws IOException;

    byte[] getEventImage(String fileName) throws IOException;


    void deleteEvent(int id);

    EventOverview findById(int id);

    Map<Integer, List<EventOverview>> sortEventsByRestaurant();
}
