package am.itspace.townrestaurantsweb.serviceWeb.impl;

import am.itspace.townrestaurantscommon.dto.reserve.CreateReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.ReserveOverview;
import am.itspace.townrestaurantscommon.mapper.ReserveMapper;
import am.itspace.townrestaurantscommon.repository.ReserveRepository;
import am.itspace.townrestaurantsweb.serviceWeb.ReserveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReserveServiceImpl implements ReserveService {

    private final ReserveRepository reserveRepository;
    private final ReserveMapper reserveMapper;

    @Override
    public Page<ReserveOverview> getReserve(Pageable pageable) {
        return null;
    }

    @Override
    public void addReserve(CreateReserveDto dto) {
    }

    @Override
    public void delete(int id) {

    }
}
