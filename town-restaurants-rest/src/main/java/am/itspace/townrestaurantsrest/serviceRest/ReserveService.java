package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.dto.reserve.CreateReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.EditReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.ReserveOverview;
import am.itspace.townrestaurantscommon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReserveService {

    void delete(int id);

    List<ReserveOverview> getByRole();

    ReserveOverview save(CreateReserveDto createReserveDto);

    ReserveOverview update(int id, EditReserveDto editReserveDto);

    List<ReserveOverview> getAllReserves(int pageNo, int pageSize, String sortBy, String sortDir);
}

