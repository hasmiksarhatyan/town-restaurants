package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.fetchRequest.FetchRequestDto;
import am.itspace.townrestaurantscommon.dto.reserve.CreateReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.EditReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.ReserveOverview;
import am.itspace.townrestaurantscommon.entity.*;
import am.itspace.townrestaurantscommon.mapper.ReserveMapper;
import am.itspace.townrestaurantscommon.repository.ReserveRepository;
import am.itspace.townrestaurantscommon.repository.RestaurantRepository;
import am.itspace.townrestaurantscommon.repository.UserRepository;
import am.itspace.townrestaurantsrest.exception.EntityAlreadyExistsException;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;
import am.itspace.townrestaurantsrest.exception.Error;
import am.itspace.townrestaurantsrest.serviceRest.ReserveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReserveServiceImpl implements ReserveService {

    private final ReserveMapper reserveMapper;
    private final UserRepository userRepository;
    private final ReserveRepository reserveRepository;
    private final RestaurantRepository restaurantRepository;
    private final SecurityContextServiceImpl securityContextService;

    @Override
    public ReserveOverview save(CreateReserveDto createReserveDto) {
        if (reserveRepository.existsByPhoneNumberAndReservedTimeAndReservedDate(createReserveDto.getPhoneNumber(), createReserveDto.getReservedTime(), createReserveDto.getReservedDate())) {
            log.info("Reserve already exists {}", createReserveDto.getPhoneNumber());
            throw new EntityAlreadyExistsException(Error.RESERVE_ALREADY_EXISTS);
        } else {
            log.info("The reserve was successfully stored in the database {}", createReserveDto.getPhoneNumber());
            Reserve reserve = reserveMapper.mapToEntity(createReserveDto);
            reserve.setStatus(ReserveStatus.PENDING);
            return reserveMapper.mapToOverview(reserveRepository.save(reserve));
        }
    }

    @Override
    public List<ReserveOverview> getAll() {
        User user = securityContextService.getUserDetails().getUser();
        List<Reserve> reserves = reserveRepository.findAll();
        if (reserves.isEmpty()) {
            log.info("Reserve not found");
            throw new EntityNotFoundException(Error.RESERVE_NOT_FOUND);
        } else {
            if (user.getRole() == Role.MANAGER) {
                log.info("Reserve successfully found");
                return reserveMapper.mapToOverviewList(reserves);
            } else if (user.getRole() == Role.RESTAURANT_OWNER) {
                reserveForOwner(user);
            } else if (user.getRole() == Role.CUSTOMER) {
                List<Reserve> reserveByUser = reserveRepository.findByUser(user);
                if (reserveByUser.isEmpty()) {
                    log.info("Reserve not found");
                    throw new EntityNotFoundException(Error.RESERVE_NOT_FOUND);
                }
                return reserveMapper.mapToOverviewList(reserveByUser);
            }
        }
        log.info("Reserve not found");
        throw new EntityNotFoundException(Error.RESERVE_NOT_FOUND);
    }

    @Override
    public List<Reserve> getReservesList(FetchRequestDto dto) {
        PageRequest pageReq = PageRequest.of(dto.getPage(), dto.getSize(), Sort.Direction.fromString(dto.getSortDir()), dto.getSort());
        Page<Reserve> reserves = reserveRepository.findByReservePhoneNumber(dto.getInstance(), pageReq);
        if (reserves.isEmpty()) {
            log.info("Reserve not found");
            throw new EntityNotFoundException(Error.RESERVE_NOT_FOUND);
        }
        return reserves.getContent();
    }

    @Override
    public ReserveOverview update(int id, EditReserveDto editReserveDto) {
        Reserve reserve = reserveRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Error.RESERVE_NOT_FOUND));
        log.info("Reserve with that id not found");
        String reservedDate = editReserveDto.getReservedDate();
        if (reservedDate != null) {
            reserve.setReservedDate(LocalDate.parse(reservedDate));
        }
        String reservedTime = editReserveDto.getReservedTime();
        if (reservedDate != null) {
            reserve.setReservedTime(LocalTime.parse(reservedTime));
        }
        int peopleCount = editReserveDto.getPeopleCount();
        if (peopleCount >= 0) {
            reserve.setPeopleCount(peopleCount);
        }
        String phoneNumber = editReserveDto.getPhoneNumber();
        if (phoneNumber != null) {
            reserve.setPhoneNumber(phoneNumber);
        }
        String status = editReserveDto.getStatus();
        if (status != null) {
            reserve.setStatus(ReserveStatus.valueOf(status));
        }
        reserveRepository.save(reserve);
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

    private List<ReserveOverview> reserveForOwner(User user) {
        List<Reserve> reservesForOwner = new ArrayList<>();
        List<Restaurant> restaurants = restaurantRepository.findAll();
        if (!restaurants.isEmpty()) {
            for (Restaurant restaurant : restaurants) {
                if (restaurant != null && restaurant.getUser().getId().equals(user.getId())) {
                    List<Reserve> byRestaurant = reserveRepository.findByRestaurantId(restaurant.getId());
                    reservesForOwner.addAll(byRestaurant);
                }
                log.info("Reserve successfully detected");
                return reserveMapper.mapToOverviewList(reservesForOwner);
            }
        }
        log.info("Reserve not found");
        throw new EntityNotFoundException(Error.RESERVE_NOT_FOUND);
    }
}
