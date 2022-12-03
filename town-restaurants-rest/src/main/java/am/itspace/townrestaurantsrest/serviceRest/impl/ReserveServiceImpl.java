package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.reserve.CreateReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.EditReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.ReserveOverview;
import am.itspace.townrestaurantscommon.entity.Reserve;
import am.itspace.townrestaurantscommon.entity.ReserveStatus;
import am.itspace.townrestaurantscommon.entity.Restaurant;
import am.itspace.townrestaurantscommon.entity.Role;
import am.itspace.townrestaurantscommon.mapper.ReserveMapper;
import am.itspace.townrestaurantscommon.repository.ReserveRepository;
import am.itspace.townrestaurantscommon.repository.RestaurantRepository;
import am.itspace.townrestaurantsrest.exception.EntityAlreadyExistsException;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;
import am.itspace.townrestaurantsrest.exception.Error;
import am.itspace.townrestaurantsrest.serviceRest.ReserveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static am.itspace.townrestaurantsrest.controller.MyControllerAdvice.getUserDetails;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReserveServiceImpl implements ReserveService {

    private final ReserveMapper reserveMapper;
    private final ReserveRepository reserveRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public ReserveOverview save(CreateReserveDto createReserveDto) {
        if (!reserveRepository.existsByPhoneNumberAndReservedTimeAndReservedDate(createReserveDto.getPhoneNumber(), createReserveDto.getReservedTime(), createReserveDto.getReservedDate())) {
            log.info("Reserve already exists {}", createReserveDto.getPhoneNumber());
            throw new EntityAlreadyExistsException(Error.RESERVE_ALREADY_EXISTS);
        }
        log.info("The reserve was successfully stored in the database {}", createReserveDto.getPhoneNumber());
        Reserve reserve = reserveMapper.mapToEntity(createReserveDto);
        reserve.setStatus(ReserveStatus.PENDING);
        return reserveMapper.mapToOverview(reserveRepository.save(reserve));
    }

    @Override
    public List<ReserveOverview> getAll() {
        List<Reserve> reserves = reserveRepository.findAll();
        if (reserves.isEmpty()) {
            log.info("Reserve not found");
            throw new EntityNotFoundException(Error.RESERVE_NOT_FOUND);
        } else {
            if (getUserDetails().getRole() == Role.MANAGER) {
                log.info("Reserve successfully detected");
                return reserveMapper.mapToOverviewList(reserves);
            } else if (getUserDetails().getRole() == Role.RESTAURANT_OWNER) {
                reserveForOwner();
            } else if (getUserDetails().getRole() == Role.CUSTOMER) {
                List<Reserve> byUser = reserveRepository.findByUser(getUserDetails());
                if (byUser.isEmpty()) {
                    log.info("Reserve not found");
                    throw new EntityNotFoundException(Error.RESERVE_NOT_FOUND);
                }
                return reserveMapper.mapToOverviewList(byUser);
            }
        }
        return null;
    }

    @Override
    public ReserveOverview update(int id, EditReserveDto editReserveDto) {
        Reserve reserve = reserveRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Error.RESERVE_NOT_FOUND));
        log.info("Reserve with that id not found");
        if (editReserveDto.getPhoneNumber() != null) {
            reserve.setPhoneNumber(editReserveDto.getPhoneNumber());
            reserve.setPeopleCount(editReserveDto.getPeopleCount());
            reserveRepository.save(reserve);
        }
        log.info("The reserve was successfully stored in the database {}", reserve.getPhoneNumber());
        return reserveMapper.mapToOverview(reserve);
    }

    @Override
    public void delete(int id) {
        if (reserveRepository.existsById(id)) {
            reserveRepository.deleteById(id);
            log.info("The reserve has been successfully deleted");
        } else {
            log.info("Reserve not found");
            throw new EntityNotFoundException(Error.RESTAURANT_NOT_FOUND);
        }
    }

    private List<ReserveOverview> reserveForOwner() {
        List<Reserve> reservesForOwner = new ArrayList<>();
        List<Restaurant> restaurants = restaurantRepository.findAll();
        if (!restaurants.isEmpty()) {
            for (Restaurant restaurant : restaurants) {
                if (restaurant != null && restaurant.getUser().getId().equals(getUserDetails().getId())) {
                    List<Reserve> byRestaurant = reserveRepository.findByRestaurantId(restaurant.getId());
                    reservesForOwner.addAll(byRestaurant);
                    log.info("Reserve successfully detected");
                    return reserveMapper.mapToOverviewList(reservesForOwner);
                } else {
                    log.info("Reserve not found");
                    throw new EntityNotFoundException(Error.RESERVE_NOT_FOUND);
                }
            }
        }
        log.info("Reserve not found");
        throw new EntityNotFoundException(Error.RESERVE_NOT_FOUND);
    }
}
