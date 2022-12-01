package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.restaurant.CreateRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.EditRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantscommon.entity.Restaurant;
import am.itspace.townrestaurantscommon.mapper.RestaurantMapper2;
import am.itspace.townrestaurantscommon.repository.RestaurantRepository;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;
import am.itspace.townrestaurantsrest.exception.Error;
import am.itspace.townrestaurantsrest.serviceRest.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper2 restaurantMapper;

    @Override
    public RestaurantOverview save(CreateRestaurantDto createRestaurantDto) {
        if (restaurantRepository.existsByName(createRestaurantDto.getName())) {
            log.info("Restaurant with that name already exists {}", createRestaurantDto.getName());
            throw new EntityNotFoundException(Error.RESTAURANT_ALREADY_EXISTS);
        }
        log.info("The Restaurant was successfully stored in the database {}", createRestaurantDto.getName());
        return restaurantMapper.mapToResponseDto(restaurantRepository.save(restaurantMapper.mapToEntity(createRestaurantDto)));
    }

    @Override
    public List<RestaurantOverview> getAll() {
        log.info("Restaurant successfully detected");
        return restaurantMapper.mapToResponseDtoList(restaurantRepository.findAll());
    }

    @Override
    public RestaurantOverview getById(int id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Error.RESTAURANT_NOT_FOUND));
        log.info("Restaurant successfully found {}", restaurant.getName());
        return restaurantMapper.mapToResponseDto(restaurant);
    }

    @Override
    public RestaurantOverview update(int id, EditRestaurantDto editRestaurantDto) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Error.RESTAURANT_NOT_FOUND));
        log.info("Restaurant with that id not found");
        if (editRestaurantDto.getName() != null) {
            restaurant.setName(editRestaurantDto.getName());
            restaurantRepository.save(restaurant);
        }
        log.info("The Restaurant was successfully stored in the database {}", restaurant.getName());
        return restaurantMapper.mapToResponseDto(restaurant);
    }

    @Override
    public void delete(int id) {
        if (restaurantRepository.existsById(id)) {
            restaurantRepository.deleteById(id);
            log.info("The Restaurant has been successfully deleted");
        }
        log.info("Restaurant not found");
        throw new EntityNotFoundException(Error.RESTAURANT_NOT_FOUND);
    }
}
