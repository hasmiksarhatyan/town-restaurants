package am.itspace.townrestaurantsweb.serviceWeb;


import am.itspace.townrestaurantscommon.dto.reserve.CreateReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.EditReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.ReserveOverview;
import am.itspace.townrestaurantscommon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ReserveService {

    Page<ReserveOverview> getAll(Pageable pageable);

    Page<ReserveOverview> getReserveByRestaurant(User user,Pageable pageable);

    Page<ReserveOverview> getReserveByUser(int id, Pageable pageable);

    void addReserve(CreateReserveDto dto, User user);

    void delete(int id);

    void editReserve(EditReserveDto dto, int id);

    ReserveOverview getById(int id);
}


