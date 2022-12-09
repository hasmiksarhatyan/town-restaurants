package am.itspace.townrestaurantsweb.serviceWeb.impl;

import am.itspace.townrestaurantscommon.dto.restaurantCategory.CreateRestaurantCategoryDto;
import am.itspace.townrestaurantscommon.dto.restaurantCategory.RestaurantCategoryOverview;
import am.itspace.townrestaurantscommon.mapper.RestaurantCategoryMapper;
import am.itspace.townrestaurantscommon.repository.RestaurantCategoryRepository;
import am.itspace.townrestaurantsweb.serviceWeb.RestaurantCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantCategoryServiceImpl implements RestaurantCategoryService {

    private final RestaurantCategoryMapper categoryMapper;
    private final RestaurantCategoryRepository restaurantCategoryRepository;

    @Override
    public Page<RestaurantCategoryOverview> findAll(Pageable pageable) {
        log.info("Category successfully found");
        List<RestaurantCategoryOverview> restaurantCategoryOverviews = categoryMapper.mapToOverviewList(restaurantCategoryRepository.findAll());
        return new PageImpl<>(restaurantCategoryOverviews);
    }

    @Override
    public List<RestaurantCategoryOverview> findAll() {
        log.info("Category successfully found");
        return categoryMapper.mapToOverviewList(restaurantCategoryRepository.findAll());
    }

    @Override
    public void addRestaurantCategory(CreateRestaurantCategoryDto dto) {
        log.info("The Category was successfully stored in the database {}", dto.getName());
        restaurantCategoryRepository.save(categoryMapper.mapToEntity(dto));
    }

    @Override
    public void deleteRestaurantCategory(int id) {
        if (restaurantCategoryRepository.existsById(id)) {
            restaurantCategoryRepository.deleteById(id);
            log.info("The category has been successfully deleted");
        } else {
            log.info("Category not found");
            throw new IllegalStateException();
        }
    }
}
