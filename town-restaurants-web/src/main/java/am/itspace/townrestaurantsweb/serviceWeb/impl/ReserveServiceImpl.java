package am.itspace.townrestaurantsweb.serviceWeb.impl;

import am.itspace.townrestaurantscommon.dto.reserve.CreateReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.EditReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.ReserveOverview;
import am.itspace.townrestaurantscommon.entity.Reserve;
import am.itspace.townrestaurantscommon.entity.ReserveStatus;
import am.itspace.townrestaurantscommon.entity.Restaurant;
import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.mapper.ReserveMapper;
import am.itspace.townrestaurantscommon.repository.ReserveRepository;
import am.itspace.townrestaurantscommon.repository.RestaurantRepository;
import am.itspace.townrestaurantsweb.serviceWeb.ReserveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static am.itspace.townrestaurantscommon.entity.ReserveStatus.PENDING;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReserveServiceImpl implements ReserveService {

    private final ReserveMapper reserveMapper;
    private final ReserveRepository reserveRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public Page<ReserveOverview> getAll(Pageable pageable) {
        return reserveRepository.findAll(pageable).map(reserveMapper::mapToOverview);
    }

    @Override
    public Page<ReserveOverview> getReserveByRestaurant(User user, Pageable pageable) {
        List<Reserve> reserves = new ArrayList<>();
        List<Restaurant> all = restaurantRepository.findAll();
        for (Restaurant restaurant : all) {
            if (restaurant != null && restaurant.getUser().getId().equals(user.getId())) {
                List<Reserve> byRestaurant = reserveRepository.findByRestaurantId(restaurant.getId());
                reserves.addAll(byRestaurant);
            }
        }
        Page<Reserve> reservePage = new PageImpl<>(reserves);
        return reservePage.map(reserveMapper::mapToOverview);
    }

    @Override
    public Page<ReserveOverview> getReserveByUser(int id, Pageable pageable) {
        return reserveRepository.findByUserId(id, pageable).map(reserveMapper::mapToOverview);
    }

    @Override
    public void addReserve(CreateReserveDto dto, User user) {
        Reserve reserve = reserveMapper.mapToEntity(dto);
        reserve.setUser(user);
        reserve.setStatus(PENDING);
        reserveRepository.save(reserve);
    }

    @Override
    public void delete(int id) {
        reserveRepository.deleteById(id);
    }

    @Override
    public void editReserve(EditReserveDto dto, int id) {
        Optional<Reserve> reserveOptional = reserveRepository.findById(id);
        if (reserveOptional.isEmpty()) {
            throw new IllegalStateException("Sorry, something went wrong, try again.");
        }
        Reserve reserve = reserveOptional.get();
        String reservedDate = dto.getReservedDate();
        if (reservedDate != null) {
            reserve.setReservedDate(LocalDate.parse(reservedDate));
        }
        String reservedTime = dto.getReservedTime();
        if (reservedDate != null) {
            reserve.setReservedTime(LocalTime.parse(reservedTime));
        }
        int peopleCount = dto.getPeopleCount();
        if (peopleCount >= 0) {
            reserve.setPeopleCount(peopleCount);
        }
        String phoneNumber = dto.getPhoneNumber();
        if (phoneNumber != null) {
            reserve.setPhoneNumber(phoneNumber);
        }
        String status = dto.getStatus();
        if (status != null) {
            reserve.setStatus(ReserveStatus.valueOf(status));
        }
        reserveRepository.save(reserve);
    }

    @Override
    public ReserveOverview getById(int id) {
        return reserveMapper.mapToOverview(reserveRepository.getReferenceById(id));
    }
}
