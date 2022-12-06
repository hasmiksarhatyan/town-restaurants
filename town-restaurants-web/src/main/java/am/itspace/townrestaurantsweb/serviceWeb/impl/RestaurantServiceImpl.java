package am.itspace.townrestaurantsweb.serviceWeb.impl;

import am.itspace.townrestaurantscommon.dto.restaurant.CreateRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.EditRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantscommon.entity.Restaurant;
import am.itspace.townrestaurantscommon.entity.RestaurantCategory;
import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.mapper.RestaurantMapperWeb;
import am.itspace.townrestaurantscommon.repository.RestaurantRepository;
import am.itspace.townrestaurantscommon.security.CurrentUser;
import am.itspace.townrestaurantsweb.serviceWeb.RestaurantService;
import am.itspace.townrestaurantsweb.utilWeb.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final FileUtil fileUtil;
    private final RestaurantMapperWeb restaurantMapper;
    private final RestaurantRepository restaurantRepository;

    public List<RestaurantOverview> findAll() {
        log.info("Restaurant successfully found");
        return restaurantMapper.mapToOverviewList(restaurantRepository.findAll());
    }

    @Override
    public Page<RestaurantOverview> findAllRestaurants(Pageable pageable) {
        log.info("Restaurant successfully found");
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
        log.info("Restaurant successfully found");
        return restaurantMapper.mapToOverviewPage(restaurantsByUser, pageable);
    }

    ////Այս մասը, եթե լինի կարագավորել, լավ կլինի
    @Override
    public void addRestaurant(CreateRestaurantDto dto, MultipartFile[] files, CurrentUser currentUser) throws IOException {
        if (restaurantRepository.existsByEmailIgnoreCase(dto.getEmail())) {
            log.info("Restaurant with that name already exists {}", dto.getName());
            throw new IllegalStateException();
        }
        dto.setPictures(fileUtil.uploadImages(files));
        User user = currentUser.getUser();
        log.info("The restaurant was successfully stored in the database {}", dto.getName());
        restaurantRepository.save(restaurantMapper.mapToEntity(dto, user));
    }

    @Override
    public byte[] getRestaurantImage(String fileName) throws IOException {
        log.info("Images successfully found");
        return FileUtil.getImage(fileName);
    }

    @Override
    public Restaurant findRestaurant(int id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(IllegalStateException::new);
        log.info("Restaurant successfully found {}", restaurant.getName());
        return restaurant;
    }

    @Override
    public void deleteRestaurant(int id) {
        if (!restaurantRepository.existsById(id)) {
            log.info("Restaurant not found");
            throw new IllegalStateException();
        }
        log.info("The restaurant has been successfully deleted");
        restaurantRepository.deleteById(id);
    }

    @Override
    public void editRestaurant(EditRestaurantDto dto, int id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(IllegalStateException::new);
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
        log.info("The restaurant was successfully stored in the database {}", restaurant.getName());
        restaurantRepository.save(restaurant);
    }

    @Override
    public RestaurantOverview getRestaurant(int id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(IllegalStateException::new);
        log.info("Restaurant successfully found {}", restaurant.getName());
        return restaurantMapper.mapToOverview(restaurant);
    }
}
