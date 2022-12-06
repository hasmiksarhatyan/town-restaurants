package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.restaurant.CreateRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantscommon.entity.Restaurant;
import am.itspace.townrestaurantscommon.entity.Role;
import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.repository.RestaurantCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RestaurantMapperWeb {

    private final RestaurantCategoryMapperWeb restaurantCategoryMapper;
    private final RestaurantCategoryRepository restaurantCategoryRepository;

    public RestaurantOverview mapToOverview(Restaurant restaurant) {
        return RestaurantOverview.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .email(restaurant.getEmail())
                .phone(restaurant.getPhone())
                .address(restaurant.getAddress())
                .deliveryPrice(restaurant.getDeliveryPrice())
                .pictures(restaurant.getPictures())
                .restaurantCategoryOverview(restaurantCategoryMapper.mapToOverview(restaurant.getRestaurantCategory()))
                .user(restaurant.getUser())
                .build();
    }

    public Restaurant mapToEntity(CreateRestaurantDto dto, User user) {
        if (user.getRole() == Role.MANAGER) {
            user.setRole(Role.MANAGER);
        }
        user.setRole(Role.RESTAURANT_OWNER);
        return Restaurant.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .restaurantCategory(restaurantCategoryRepository.getReferenceById(dto.getRestaurantCategoryId()))
                .deliveryPrice(dto.getDeliveryPrice())
                .pictures(dto.getPictures())
                .user(user)
                .build();
    }

    public Page<RestaurantOverview> mapToOverviewPage(Page<Restaurant> allRestaurants, Pageable pageable) {
        List<RestaurantOverview> restaurantOverviews = new ArrayList<>();
        for (Restaurant restaurant : allRestaurants) {
            restaurantOverviews.add(mapToOverview(restaurant));
        }
        return new PageImpl<>(restaurantOverviews, pageable, restaurantOverviews.size());
    }


    public List<RestaurantOverview> mapToOverviewList(List<Restaurant> all) {
        List<RestaurantOverview> restaurantOverviews = new ArrayList<>();
        for (Restaurant restaurant : all) {
            restaurantOverviews.add(mapToOverview(restaurant));
        }
        return restaurantOverviews;
    }
}

