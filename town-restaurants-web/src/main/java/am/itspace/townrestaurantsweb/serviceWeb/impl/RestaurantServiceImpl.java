package am.itspace.townrestaurantsweb.serviceWeb.impl;

import am.itspace.townrestaurantscommon.dto.restaurant.CreateRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.EditRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantscommon.entity.Restaurant;
import am.itspace.townrestaurantscommon.entity.RestaurantCategory;
import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.mapper.RestaurantMapper;
import am.itspace.townrestaurantscommon.repository.RestaurantRepository;
import am.itspace.townrestaurantscommon.security.CurrentUser;
import am.itspace.townrestaurantsweb.serviceWeb.RestaurantService;
import am.itspace.townrestaurantsweb.utilWeb.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantMapper restaurantMapper;
    private final RestaurantRepository restaurantRepository;

    public List<RestaurantOverview> findAll() {
        return restaurantMapper.mapToOverviewList(restaurantRepository.findAll());
    }

    @Override
    public Page<RestaurantOverview> findAllRestaurants(Pageable pageable) {
        return restaurantMapper.mapToOverviewPage(restaurantRepository.findAll(pageable), pageable);

        //        Page<Restaurant> restaurants = restaurantRepository.findAll(pageable);
//        List<RestaurantOverview> restaurantOverviews = new ArrayList<>();
//        for (Restaurant restaurant : restaurants) {
//            restaurantOverviews.add(restaurantMapper.mapToOverview(restaurant));
//        }
//        return new PageImpl<>(restaurantOverviews, pageable, restaurantOverviews.size());
    }

    @Override
    public Page<RestaurantOverview> getRestaurantsByUser(User user, Pageable pageable) {
        Page<Restaurant> restaurantsByUser = restaurantRepository.findRestaurantsByUser(user, pageable);
        if (restaurantsByUser.isEmpty()) {
            throw new IllegalStateException("You don't have a restaurant");
        }
        return restaurantMapper.mapToOverviewPage(restaurantsByUser, pageable);
    }

    @Override
    public void addRestaurant(CreateRestaurantDto dto, MultipartFile[] files, CurrentUser currentUser) throws IOException {
        if (restaurantRepository.existsByEmailIgnoreCase(dto.getEmail())) {
            throw new IllegalStateException("Email already in use");
        }
        dto.setPictures(FileUtil.uploadImages(files));
        User user = currentUser.getUser();
        restaurantRepository.save(restaurantMapper.mapToEntity(dto, user));
    }

    @Override
    public byte[] getRestaurantImage(String fileName) throws IOException {
        return FileUtil.getImage(fileName);
    }

    @Override
    public void deleteRestaurant(int id) {
        if (!restaurantRepository.existsById(id)) {
            throw new IllegalStateException();
        }
        restaurantRepository.deleteById(id);
    }

    @Override
    public void editRestaurant(EditRestaurantDto dto, int id) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);
        if (restaurantOptional.isEmpty()) {
            throw new IllegalStateException("Sorry, something went wrong, try again.");
        }
        Restaurant restaurant = restaurantOptional.get();
        String name = dto.getName();
        if (StringUtils.hasText(name)) {
            restaurant.setName(name);
        }
        String email = dto.getEmail();
        if (StringUtils.hasText(email)) {
            restaurant.setEmail(email);
        }
        String phone = dto.getPhone();
        if (StringUtils.hasText(phone)) {
            restaurant.setPhone(phone);
        }
        String address = dto.getAddress();
        if (StringUtils.hasText(address)) {
            restaurant.setAddress(address);
        }
        Double deliveryPrice = dto.getDeliveryPrice();
        if (deliveryPrice != null) {
            restaurant.setDeliveryPrice(deliveryPrice);
        }
        RestaurantCategory restaurantCategory = dto.getRestaurantCategory();
        if (restaurantCategory != null) {
            restaurant.setRestaurantCategory(restaurantCategory);
        }
        restaurantRepository.save(restaurant);
    }

    @Override
    public RestaurantOverview getRestaurant(int id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        if (restaurant.isEmpty()) {
            throw new IllegalStateException("There is no restaurant");
        }
        return restaurantMapper.mapToOverview(restaurant.get());
    }

    @Override
    public Restaurant findRestaurant(int id) {
        Optional<Restaurant> byId = restaurantRepository.findById(id);
        if (byId.isEmpty()) {
            throw new IllegalStateException("There is no restaurant");
        }
        return byId.get();
    }
}
