package am.itspace.townrestaurantsweb.serviceWeb;

import am.itspace.townrestaurantscommon.dto.restaurant.CreateRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.EditRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantscommon.entity.Restaurant;
import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.security.CurrentUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RestaurantService {

    List<RestaurantOverview> findAll();

    Page<RestaurantOverview> findAllRestaurants(Pageable pageable);

    void addRestaurant(CreateRestaurantDto restaurantDto, MultipartFile[] files, CurrentUser currentUser) throws IOException;

    byte[] getRestaurantImage(String fileName) throws IOException;

    void deleteRestaurant(int id);

    Page<RestaurantOverview> getRestaurantsByUser(User user, Pageable pageable);

    void editRestaurant(EditRestaurantDto dto, int id);

    RestaurantOverview getRestaurant(int id);

    Restaurant findRestaurant(int id);
}


