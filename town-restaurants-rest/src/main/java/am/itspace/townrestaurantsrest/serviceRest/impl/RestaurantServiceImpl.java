package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.FileDto;
import am.itspace.townrestaurantscommon.dto.restaurant.CreateRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.EditRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantRequestDto;
import am.itspace.townrestaurantscommon.entity.Restaurant;
import am.itspace.townrestaurantscommon.entity.RestaurantCategory;
import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.mapper.RestaurantMapper;
import am.itspace.townrestaurantscommon.repository.RestaurantCategoryRepository;
import am.itspace.townrestaurantscommon.repository.RestaurantRepository;
import am.itspace.townrestaurantscommon.utilCommon.FileUtil;
import am.itspace.townrestaurantsrest.exception.Error;
import am.itspace.townrestaurantsrest.exception.*;
import am.itspace.townrestaurantsrest.serviceRest.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static am.itspace.townrestaurantsrest.exception.Error.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final FileUtil fileUtil;
    private final RestaurantMapper restaurantMapper;
    private final RestaurantRepository restaurantRepository;
    private final SecurityContextServiceImpl securityContextService;
    private final RestaurantCategoryRepository restaurantCategoryRepository;

    @Override
    public RestaurantOverview save(RestaurantRequestDto dto) {
        CreateRestaurantDto createRestaurantDto = dto.getCreateRestaurantDto();
        FileDto fileDto = dto.getFileDto();
        if (restaurantRepository.existsByName(createRestaurantDto.getName())) {
            log.info("Restaurant with that name already exists {}", createRestaurantDto.getName());
            throw new EntityAlreadyExistsException(Error.RESTAURANT_ALREADY_EXISTS);
        }
        if (fileDto.getFiles() != null) {
            try {
                MultipartFile[] files = fileDto.getFiles();
                for (MultipartFile file : files) {
                    if (!file.isEmpty() && file.getSize() > 0) {
                        if (file.getContentType() != null && !file.getContentType().contains("image")) {
                            throw new MyFileNotFoundException(FILE_NOT_FOUND);
                        }
                    }
                }
                createRestaurantDto.setPictures(fileUtil.uploadImages(files));
            } catch (IOException e) {
                throw new FileStorageException(FILE_UPLOAD_FAILED);
            }
        }
        log.info("The restaurant was successfully stored in the database {}", createRestaurantDto.getName());
        return restaurantMapper.mapToResponseDto(restaurantRepository.save(restaurantMapper.mapToEntity(createRestaurantDto)));
    }

    @Override
    public List<RestaurantOverview> getAll(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Restaurant> restaurants = restaurantRepository.findAll(pageable);
        return findRestaurant(restaurants);
    }

    @Override
    public List<RestaurantOverview> getRestaurantsByUser(int pageNo, int pageSize, String sortBy, String sortDir) {
        try {
            User user = securityContextService.getUserDetails().getUser();
            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();
            Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
            Page<Restaurant> restaurants = restaurantRepository.findRestaurantsByUser(user, pageable);
            return findRestaurant(restaurants);
        } catch (ClassCastException e) {
            throw new AuthenticationException(NEEDS_AUTHENTICATION);
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
        Integer categoryId = editRestaurantDto.getRestaurantCategoryId();
        if (categoryId != null) {
            Optional<RestaurantCategory> categoryOptional = restaurantCategoryRepository.findById(categoryId);
            categoryOptional.ifPresent(restaurant::setRestaurantCategory);
        }
        restaurantRepository.save(restaurant);
        log.info("The restaurant was successfully stored in the database {}", restaurant.getName());
        return restaurantMapper.mapToResponseDto(restaurant);
    }

    @Override
    public byte[] getRestaurantImage(String fileName) {
        try {
            log.info("Images successfully found");
            return fileUtil.getImage(fileName);
        } catch (IOException e) {
            throw new MyFileNotFoundException(FILE_NOT_FOUND);
        }
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

    private List<RestaurantOverview> findRestaurant(Page<Restaurant> restaurants) {
        if (restaurants.isEmpty()) {
            log.info("Restaurant not found");
            throw new EntityNotFoundException(Error.RESTAURANT_NOT_FOUND);
        }
        List<Restaurant> listOfRestaurants = restaurants.getContent();
        log.info("Restaurant successfully found");
        return new ArrayList<>(restaurantMapper.mapToResponseDtoList(listOfRestaurants));
    }
}
