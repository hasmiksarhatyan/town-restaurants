package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.restaurant.CreateRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.EditRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantscommon.entity.Restaurant;
import am.itspace.townrestaurantscommon.entity.RestaurantCategory;
import am.itspace.townrestaurantscommon.mapper.RestaurantMapper;
import am.itspace.townrestaurantscommon.repository.RestaurantRepository;
import am.itspace.townrestaurantsrest.exception.EntityAlreadyExistsException;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;
import am.itspace.townrestaurantsrest.exception.Error;
import am.itspace.townrestaurantsrest.serviceRest.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantMapper restaurantMapper;
    private final RestaurantRepository restaurantRepository;

    @Override
    public RestaurantOverview save(CreateRestaurantDto createRestaurantDto) {
        if (restaurantRepository.existsByName(createRestaurantDto.getName())) {
            log.info("Restaurant with that name already exists {}", createRestaurantDto.getName());
            throw new EntityAlreadyExistsException(Error.RESTAURANT_ALREADY_EXISTS);
        }
        log.info("The restaurant was successfully stored in the database {}", createRestaurantDto.getName());
        return restaurantMapper.mapToResponseDto(restaurantRepository.save(restaurantMapper.mapToEntity(createRestaurantDto)));
    }

    @Override
    public List<RestaurantOverview> getAll() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        if (restaurants.isEmpty()) {
            log.info("Restaurant not found");
            throw new EntityNotFoundException(Error.RESTAURANT_NOT_FOUND);
        } else {
            log.info("Restaurant successfully found");
            return restaurantMapper.mapToResponseDtoList(restaurants);
        }
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
        String name = editRestaurantDto.getName();
        if (StringUtils.hasText(name)) {
            restaurant.setName(name);
        }
        String email = editRestaurantDto.getEmail();
        if (StringUtils.hasText(email)) {
            restaurant.setEmail(email);
        }
        String phone = editRestaurantDto.getPhone();
        if (StringUtils.hasText(phone)) {
            restaurant.setPhone(phone);
        }
        String address = editRestaurantDto.getAddress();
        if (StringUtils.hasText(address)) {
            restaurant.setAddress(address);
        }
        Double deliveryPrice = editRestaurantDto.getDeliveryPrice();
        if (deliveryPrice != null) {
            restaurant.setDeliveryPrice(deliveryPrice);
        }
        RestaurantCategory restaurantCategory = editRestaurantDto.getRestaurantCategory();
        if (restaurantCategory != null) {
            restaurant.setRestaurantCategory(restaurantCategory);
        }
        restaurantRepository.save(restaurant);
        log.info("The restaurant was successfully stored in the database {}", restaurant.getName());
        return restaurantMapper.mapToResponseDto(restaurant);
    }

    @Override
    public void delete(int id) {
        if (restaurantRepository.existsById(id)) {
            restaurantRepository.deleteById(id);
            log.info("The restaurant has been successfully deleted");
        } else {
            log.info("Restaurant not found");
            throw new EntityNotFoundException(Error.RESTAURANT_NOT_FOUND);
        }
    }
}
