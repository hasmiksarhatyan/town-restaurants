package am.itspace.townrestaurantsweb.serviceWeb.impl;

import am.itspace.townrestaurantscommon.dto.restaurantCategory.CreateRestaurantCategoryDto;
import am.itspace.townrestaurantscommon.dto.restaurantCategory.RestaurantCategoryOverview;
import am.itspace.townrestaurantscommon.entity.RestaurantCategory;
import am.itspace.townrestaurantscommon.mapper.RestaurantCategoryMapper;
import am.itspace.townrestaurantscommon.repository.RestaurantCategoryRepository;
import am.itspace.townrestaurantsweb.serviceWeb.RestaurantCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantCategoryServiceImpl implements RestaurantCategoryService {

    private final RestaurantCategoryMapper categoryMapper;
    private final RestaurantCategoryRepository restaurantCategoryRepository;

    @Override
    public Page<RestaurantCategoryOverview> findAll(Pageable pageable) {
        return categoryMapper.mapToOverviewPage(restaurantCategoryRepository.findAll(pageable), pageable);
    }

    @Override
    public List<RestaurantCategoryOverview> findAll() {
        List<RestaurantCategory> allCategories = restaurantCategoryRepository.findAll();
        List<RestaurantCategoryOverview> restaurantCategoryOverviews = new ArrayList<>();
        for (RestaurantCategory category : allCategories) {
            restaurantCategoryOverviews.add(categoryMapper.mapToOverview(category));
        }
        return restaurantCategoryOverviews;
    }

    @Override
    public void addRestaurantCategory(CreateRestaurantCategoryDto dto) {
        restaurantCategoryRepository.save(categoryMapper.mapToEntity(dto));
    }

    @Override
    public void deleteRestaurantCategory(int id) {
        if (!restaurantCategoryRepository.existsById(id)) {
            throw new IllegalStateException();
        }
        restaurantCategoryRepository.deleteById(id);
    }
}
