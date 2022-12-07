package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.dto.fetchRequest.FetchRequestDto;
import am.itspace.townrestaurantscommon.dto.reserve.CreateReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.EditReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.ReserveOverview;
import am.itspace.townrestaurantscommon.entity.Reserve;

import java.util.List;

public interface ReserveService {

    void delete(int id);

    List<ReserveOverview> getAll();

    ReserveOverview save(CreateReserveDto createReserveDto);

    ReserveOverview update(int id, EditReserveDto editReserveDto);

    List<Reserve> getReservesList(FetchRequestDto fetchRequestDto);
}

