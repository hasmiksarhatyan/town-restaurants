package am.itspace.townrestaurantsweb.serviceWeb;


import am.itspace.townrestaurantscommon.dto.reserve.CreateReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.ReserveOverview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ReserveService {

    Page<ReserveOverview> getReserve(Pageable pageable);

    void addReserve(CreateReserveDto dto);

    void delete(int id);
}


