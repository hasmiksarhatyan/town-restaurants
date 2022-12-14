package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.restaurantCategory.CreateRestaurantCategoryDto;
import am.itspace.townrestaurantscommon.dto.restaurantCategory.EditRestaurantCategoryDto;
import am.itspace.townrestaurantscommon.dto.restaurantCategory.RestaurantCategoryOverview;
import am.itspace.townrestaurantscommon.entity.RestaurantCategory;
import am.itspace.townrestaurantscommon.mapper.RestaurantCategoryMapper;
import am.itspace.townrestaurantscommon.repository.RestaurantCategoryRepository;
import am.itspace.townrestaurantsrest.exception.EntityAlreadyExistsException;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;
import am.itspace.townrestaurantsrest.exception.Error;
import am.itspace.townrestaurantsrest.serviceRest.RestaurantCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantCategoryServiceImpl implements RestaurantCategoryService {

    private final RestaurantCategoryMapper restaurantCategoryMapper;
    private final RestaurantCategoryRepository restaurantCategoryRepository;

    @Override
    public RestaurantCategoryOverview save(CreateRestaurantCategoryDto createRestaurantCategoryDto) {
        if (restaurantCategoryRepository.existsByName(createRestaurantCategoryDto.getName())) {
            log.info("Category with that name already exists {}", createRestaurantCategoryDto.getName());
            throw new EntityAlreadyExistsException(Error.RESTAURANT_CATEGORY_ALREADY_EXISTS);
        }
        log.info("The Category was successfully stored in the database {}", createRestaurantCategoryDto.getName());
        return restaurantCategoryMapper.mapToOverview(restaurantCategoryRepository.save(restaurantCategoryMapper.mapToEntity(createRestaurantCategoryDto)));
    }

    @Override
    public List<RestaurantCategoryOverview> getAllCategories(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<RestaurantCategory> categories = restaurantCategoryRepository.findAll(pageable);
        if (categories.isEmpty()) {
            log.info("Category not found");
            throw new EntityNotFoundException(Error.RESTAURANT_CATEGORY_NOT_FOUND);
        }
        List<RestaurantCategory> listOfCategories = categories.getContent();
        log.info("Category successfully found");
        return new ArrayList<>(restaurantCategoryMapper.mapToOverviewList(listOfCategories));
    }

    @Override
    public RestaurantCategoryOverview getById(int id) {
        RestaurantCategory restaurantCategory = restaurantCategoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Error.RESTAURANT_CATEGORY_NOT_FOUND));
        log.info("Category successfully found {}", restaurantCategory.getName());
        return restaurantCategoryMapper.mapToOverview(restaurantCategory);
    }

    @Override
    public RestaurantCategoryOverview update(int id, EditRestaurantCategoryDto editRestaurantCategoryDto) {
        RestaurantCategory restaurantCategory = restaurantCategoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Error.RESTAURANT_CATEGORY_NOT_FOUND));
        log.info("Category with that id not found");
        if (editRestaurantCategoryDto.getName() != null) {
            restaurantCategory.setName(editRestaurantCategoryDto.getName());
            restaurantCategoryRepository.save(restaurantCategory);
        }
        log.info("The category was successfully stored in the database {}", restaurantCategory.getName());
        return restaurantCategoryMapper.mapToOverview(restaurantCategory);
    }

    @Override
    public void delete(int id) {
        if (restaurantCategoryRepository.existsById(id)) {
            restaurantCategoryRepository.deleteById(id);
            log.info("The category has been successfully deleted");
        } else {
            log.info("Category not found");
            throw new EntityNotFoundException(Error.RESTAURANT_CATEGORY_NOT_FOUND);
        }
    }
}