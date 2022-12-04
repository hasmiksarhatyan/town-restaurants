package am.itspace.townrestaurantsweb.serviceWeb;

import am.itspace.townrestaurantscommon.dto.reserve.CreateReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.EditReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.ReserveOverview;
import am.itspace.townrestaurantscommon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ReserveService {

    void delete(int id);

    ReserveOverview getById(int id);

    void editReserve(EditReserveDto dto, int id);

    Page<ReserveOverview> getAll(Pageable pageable);

    void addReserve(CreateReserveDto dto, User user);

    Page<ReserveOverview> getReserveByUser(int id, Pageable pageable);

    Page<ReserveOverview> getReserveByRestaurant(User user, Pageable pageable);
}


