package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.dto.reserve.CreateReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.EditReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.ReserveOverview;

import java.util.List;

public interface ReserveService {

    void delete(int id);

    List<ReserveOverview> getAll(int userId);

    ReserveOverview save(CreateReserveDto createReserveDto);

    ReserveOverview update(int id, EditReserveDto editReserveDto);
}

